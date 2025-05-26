import React, { useState, useEffect } from 'react';
import { Play, Square, Settings, Ticket, Users, Monitor, Plus, Trash2, Eye, EyeOff } from 'lucide-react';

const API_BASE_URL = 'http://localhost:8081/api';

const TicketSystemApp = () => {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [configurations, setConfigurations] = useState([]);
  const [activeConfig, setActiveConfig] = useState(null);
  const [systemStatus, setSystemStatus] = useState({
    availableTickets: 0,
    totalTickets: 0,
    purchasedTickets: 0,
    maxCapacity: 0,
    systemRunning: false
  });
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  // Fetch system status periodically
  useEffect(() => {
    const interval = setInterval(() => {
      if (activeTab === 'dashboard' || systemStatus.systemRunning) {
        fetchSystemStatus();
      }
    }, 2000);
    return () => clearInterval(interval);
  }, [activeTab, systemStatus.systemRunning]);

  // Initial data fetch
  useEffect(() => {
    fetchConfigurations();
    fetchActiveConfiguration();
    fetchSystemStatus();
  }, []);

  const showMessage = (msg, type = 'info') => {
    setMessage(msg);
    setTimeout(() => setMessage(''), 3000);
  };

  // API Functions
  const fetchConfigurations = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/configurations`);
      const data = await response.json();
      setConfigurations(data);
    } catch (error) {
      showMessage('Error fetching configurations', 'error');
    }
  };

  const fetchActiveConfiguration = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/configurations/active`);
      if (response.ok) {
        const data = await response.json();
        setActiveConfig(data);
      } else {
        setActiveConfig(null);
      }
    } catch (error) {
      setActiveConfig(null);
    }
  };

  const fetchSystemStatus = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/system/status`);
      const data = await response.json();
      setSystemStatus(data);
    } catch (error) {
      console.error('Error fetching system status');
    }
  };

  const fetchTickets = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/tickets`);
      const data = await response.json();
      setTickets(data);
    } catch (error) {
      showMessage('Error fetching tickets', 'error');
    }
  };

  const startSystem = async (configId) => {
    setLoading(true);
    try {
      const response = await fetch(`${API_BASE_URL}/system/start`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ configurationId: configId, action: 'START' })
      });
      const result = await response.text();
      showMessage(result, response.ok ? 'success' : 'error');
      if (response.ok) {
        fetchSystemStatus();
      }
    } catch (error) {
      showMessage('Error starting system', 'error');
    }
    setLoading(false);
  };

  const stopSystem = async () => {
    setLoading(true);
    try {
      const response = await fetch(`${API_BASE_URL}/system/stop`, {
        method: 'POST'
      });
      const result = await response.text();
      showMessage(result, 'success');
      fetchSystemStatus();
    } catch (error) {
      showMessage('Error stopping system', 'error');
    }
    setLoading(false);
  };

  const purchaseTicket = async () => {
    const customerId = `CUSTOMER-${Date.now()}`;
    try {
      const response = await fetch(`${API_BASE_URL}/tickets/purchase?customerId=${customerId}`, {
        method: 'POST'
      });
      if (response.ok) {
        const ticket = await response.json();
        showMessage(`Ticket ${ticket.ticketNumber} purchased successfully!`, 'success');
        fetchSystemStatus();
      } else {
        showMessage('No tickets available', 'error');
      }
    } catch (error) {
      showMessage('Error purchasing ticket', 'error');
    }
  };

  const addTickets = async (count) => {
    try {
      const response = await fetch(`${API_BASE_URL}/tickets/add/${count}`, {
        method: 'POST'
      });
      if (response.ok) {
        showMessage(`${count} tickets added successfully!`, 'success');
        fetchSystemStatus();
      }
    } catch (error) {
      showMessage('Error adding tickets', 'error');
    }
  };

  // Components
  const ConfigurationForm = () => {
    const [config, setConfig] = useState({
      totalTickets: 100,
      ticketReleaseRateMs: 2000,
      customerRetrievalRateMs: 1500,
      maxTicketCapacity: 50,
      configName: '',
      isActive: false
    });

    const handleSubmit = async () => {
      if (!config.configName.trim()) {
        showMessage('Configuration name is required', 'error');
        return;
      }
      setLoading(true);
      try {
        const response = await fetch(`${API_BASE_URL}/configurations`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(config)
        });
        if (response.ok) {
          showMessage('Configuration created successfully!', 'success');
          fetchConfigurations();
          fetchActiveConfiguration();
          setConfig({
            totalTickets: 100,
            ticketReleaseRateMs: 2000,
            customerRetrievalRateMs: 1500,
            maxTicketCapacity: 50,
            configName: '',
            isActive: false
          });
        }
      } catch (error) {
        showMessage('Error creating configuration', 'error');
      }
      setLoading(false);
    };

    return (
      <div className="bg-white rounded-lg shadow-md p-6">
        <h3 className="text-lg font-semibold mb-4 flex items-center">
          <Settings className="mr-2" size={20} />
          Create Configuration
        </h3>
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium mb-1">Configuration Name</label>
            <input
              type="text"
              className="w-full p-2 border rounded-md"
              value={config.configName}
              onChange={(e) => setConfig({...config, configName: e.target.value})}
              required
            />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium mb-1">Total Tickets</label>
              <input
                type="number"
                className="w-full p-2 border rounded-md"
                value={config.totalTickets}
                onChange={(e) => setConfig({...config, totalTickets: parseInt(e.target.value)})}
                min="1"
              />
            </div>
            <div>
              <label className="block text-sm font-medium mb-1">Max Capacity</label>
              <input
                type="number"
                className="w-full p-2 border rounded-md"
                value={config.maxTicketCapacity}
                onChange={(e) => setConfig({...config, maxTicketCapacity: parseInt(e.target.value)})}
                min="1"
              />
            </div>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium mb-1">Release Rate (ms)</label>
              <input
                type="number"
                className="w-full p-2 border rounded-md"
                value={config.ticketReleaseRateMs}
                onChange={(e) => setConfig({...config, ticketReleaseRateMs: parseInt(e.target.value)})}
                min="100"
              />
            </div>
            <div>
              <label className="block text-sm font-medium mb-1">Purchase Rate (ms)</label>
              <input
                type="number"
                className="w-full p-2 border rounded-md"
                value={config.customerRetrievalRateMs}
                onChange={(e) => setConfig({...config, customerRetrievalRateMs: parseInt(e.target.value)})}
                min="100"
              />
            </div>
          </div>
          <div className="flex items-center">
            <input
              type="checkbox"
              id="isActive"
              checked={config.isActive}
              onChange={(e) => setConfig({...config, isActive: e.target.checked})}
              className="mr-2"
            />
            <label htmlFor="isActive" className="text-sm font-medium">Set as Active</label>
          </div>
          <button
            type="button"
            onClick={handleSubmit}
            disabled={loading}
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 disabled:opacity-50"
          >
            {loading ? 'Creating...' : 'Create Configuration'}
          </button>
        </div>
      </div>
    );
  };

  const ConfigurationList = () => {
    const activateConfig = async (id) => {
      try {
        const response = await fetch(`${API_BASE_URL}/configurations/${id}/activate`, {
          method: 'PUT'
        });
        if (response.ok) {
          showMessage('Configuration activated successfully!', 'success');
          fetchConfigurations();
          fetchActiveConfiguration();
        }
      } catch (error) {
        showMessage('Error activating configuration', 'error');
      }
    };

    const deleteConfig = async (id) => {
      if (window.confirm('Are you sure you want to delete this configuration?')) {
        try {
          const response = await fetch(`${API_BASE_URL}/configurations/${id}`, {
            method: 'DELETE'
          });
          if (response.ok) {
            showMessage('Configuration deleted successfully!', 'success');
            fetchConfigurations();
            fetchActiveConfiguration();
          }
        } catch (error) {
          showMessage('Error deleting configuration', 'error');
        }
      }
    };

    return (
      <div className="bg-white rounded-lg shadow-md p-6">
        <h3 className="text-lg font-semibold mb-4">Configurations</h3>
        <div className="space-y-3">
          {configurations.map((config) => (
            <div key={config.id} className={`p-4 border rounded-md ${config.active ? 'border-green-500 bg-green-50' : 'border-gray-200'}`}>
              <div className="flex justify-between items-start">
                <div>
                  <h4 className="font-medium">{config.configName}</h4>
                  <div className="text-sm text-gray-600 mt-1">
                    <p>Total: {config.totalTickets} | Capacity: {config.maxTicketCapacity}</p>
                    <p>Release: {config.ticketReleaseRateMs}ms | Purchase: {config.customerRetrievalRateMs}ms</p>
                  </div>
                  {config.active && <span className="inline-block bg-green-100 text-green-800 text-xs px-2 py-1 rounded-full mt-2">Active</span>}
                </div>
                <div className="flex space-x-2">
                  {!config.active && (
                    <button
                      onClick={() => activateConfig(config.id)}
                      className="text-blue-600 hover:text-blue-800"
                      title="Activate"
                    >
                      <Play size={16} />
                    </button>
                  )}
                  <button
                    onClick={() => deleteConfig(config.id)}
                    className="text-red-600 hover:text-red-800"
                    title="Delete"
                  >
                    <Trash2 size={16} />
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const Dashboard = () => {
    return (
      <div className="space-y-6">
        {/* System Status */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <h3 className="text-lg font-semibold mb-4 flex items-center">
            <Monitor className="mr-2" size={20} />
            System Status
          </h3>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
            <div className="bg-blue-50 p-4 rounded-lg">
              <div className="text-2xl font-bold text-blue-600">{systemStatus.availableTickets}</div>
              <div className="text-sm text-gray-600">Available</div>
            </div>
            <div className="bg-green-50 p-4 rounded-lg">
              <div className="text-2xl font-bold text-green-600">{systemStatus.purchasedTickets}</div>
              <div className="text-sm text-gray-600">Purchased</div>
            </div>
            <div className="bg-purple-50 p-4 rounded-lg">
              <div className="text-2xl font-bold text-purple-600">{systemStatus.totalTickets}</div>
              <div className="text-sm text-gray-600">Total</div>
            </div>
            <div className="bg-orange-50 p-4 rounded-lg">
              <div className="text-2xl font-bold text-orange-600">{systemStatus.maxCapacity}</div>
              <div className="text-sm text-gray-600">Max Capacity</div>
            </div>
          </div>
          
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-4">
              <div className={`flex items-center ${systemStatus.systemRunning ? 'text-green-600' : 'text-red-600'}`}>
                <div className={`w-3 h-3 rounded-full mr-2 ${systemStatus.systemRunning ? 'bg-green-500' : 'bg-red-500'}`}></div>
                {systemStatus.systemRunning ? 'System Running' : 'System Stopped'}
              </div>
            </div>
            
            <div className="flex space-x-2">
              {!systemStatus.systemRunning ? (
                <button
                  onClick={() => activeConfig && startSystem(activeConfig.id)}
                  disabled={!activeConfig || loading}
                  className="flex items-center bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 disabled:opacity-50"
                >
                  <Play className="mr-1" size={16} />
                  Start System
                </button>
              ) : (
                <button
                  onClick={stopSystem}
                  disabled={loading}
                  className="flex items-center bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700 disabled:opacity-50"
                >
                  <Square className="mr-1" size={16} />
                  Stop System
                </button>
              )}
            </div>
          </div>
          
          {!activeConfig && (
            <div className="mt-4 p-3 bg-yellow-100 border border-yellow-400 rounded-md">
              <p className="text-yellow-800 text-sm">No active configuration. Please create and activate a configuration first.</p>
            </div>
          )}
        </div>

        {/* Manual Controls */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <h3 className="text-lg font-semibold mb-4 flex items-center">
            <Users className="mr-2" size={20} />
            Manual Controls
          </h3>
          <div className="flex flex-wrap gap-4">
            <button
              onClick={() => addTickets(5)}
              className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
            >
              <Plus className="mr-1" size={16} />
              Add 5 Tickets
            </button>
            <button
              onClick={() => addTickets(10)}
              className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
            >
              <Plus className="mr-1" size={16} />
              Add 10 Tickets
            </button>
            <button
              onClick={purchaseTicket}
              className="flex items-center bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700"
            >
              <Ticket className="mr-1" size={16} />
              Purchase Ticket
            </button>
          </div>
        </div>

        {/* Active Configuration Display */}
        {activeConfig && (
          <div className="bg-white rounded-lg shadow-md p-6">
            <h3 className="text-lg font-semibold mb-4">Active Configuration</h3>
            <div className="bg-green-50 p-4 rounded-lg">
              <h4 className="font-medium text-green-800">{activeConfig.configName}</h4>
              <div className="text-sm text-green-700 mt-2 grid grid-cols-2 gap-2">
                <p>Total Tickets: {activeConfig.totalTickets}</p>
                <p>Max Capacity: {activeConfig.maxTicketCapacity}</p>
                <p>Release Rate: {activeConfig.ticketReleaseRateMs}ms</p>
                <p>Purchase Rate: {activeConfig.customerRetrievalRateMs}ms</p>
              </div>
            </div>
          </div>
        )}
      </div>
    );
  };

  const TicketManager = () => {
    const [showTickets, setShowTickets] = useState(false);

    useEffect(() => {
      if (activeTab === 'tickets' && showTickets) {
        fetchTickets();
      }
    }, [activeTab, showTickets]);

    const clearAllTickets = async () => {
      if (window.confirm('Are you sure you want to clear all tickets?')) {
        try {
          const response = await fetch(`${API_BASE_URL}/tickets/clear`, {
            method: 'DELETE'
          });
          if (response.ok) {
            showMessage('All tickets cleared successfully!', 'success');
            setTickets([]);
            fetchSystemStatus();
          }
        } catch (error) {
          showMessage('Error clearing tickets', 'error');
        }
      }
    };

    return (
      <div className="space-y-6">
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex justify-between items-center mb-4">
            <h3 className="text-lg font-semibold flex items-center">
              <Ticket className="mr-2" size={20} />
              Ticket Management
            </h3>
            <div className="flex space-x-2">
              <button
                onClick={() => setShowTickets(!showTickets)}
                className={`flex items-center px-4 py-2 rounded-md ${showTickets ? 'bg-gray-600 text-white' : 'bg-gray-200 text-gray-700'}`}
              >
                {showTickets ? <EyeOff className="mr-1" size={16} /> : <Eye className="mr-1" size={16} />}
                {showTickets ? 'Hide Tickets' : 'Show Tickets'}
              </button>
              <button
                onClick={clearAllTickets}
                className="flex items-center bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-700"
              >
                <Trash2 className="mr-1" size={16} />
                Clear All
              </button>
            </div>
          </div>

          {showTickets && (
            <div className="max-h-96 overflow-y-auto">
              <div className="grid gap-2">
                {tickets.map((ticket) => (
                  <div key={ticket.id} className={`p-3 border rounded-md ${
                    ticket.status === 'AVAILABLE' ? 'border-green-200 bg-green-50' :
                    ticket.status === 'PURCHASED' ? 'border-blue-200 bg-blue-50' :
                    'border-gray-200 bg-gray-50'
                  }`}>
                    <div className="flex justify-between items-center">
                      <div>
                        <span className="font-medium">{ticket.ticketNumber}</span>
                        <span className={`ml-2 px-2 py-1 text-xs rounded-full ${
                          ticket.status === 'AVAILABLE' ? 'bg-green-100 text-green-800' :
                          ticket.status === 'PURCHASED' ? 'bg-blue-100 text-blue-800' :
                          'bg-gray-100 text-gray-800'
                        }`}>
                          {ticket.status}
                        </span>
                      </div>
                      <div className="text-sm text-gray-600">
                        {ticket.customerId && <span>Customer: {ticket.customerId}</span>}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    );
  };

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <h1 className="text-2xl font-bold text-gray-900">Ticket System Management</h1>
            {message && (
              <div className={`px-4 py-2 rounded-md text-sm ${
                message.includes('Error') ? 'bg-red-100 text-red-800' :
                message.includes('success') ? 'bg-green-100 text-green-800' :
                'bg-blue-100 text-blue-800'
              }`}>
                {message}
              </div>
            )}
          </div>
        </div>
      </header>

      {/* Navigation */}
      <nav className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex space-x-8">
            {[
              { id: 'dashboard', label: 'Dashboard', icon: Monitor },
              { id: 'config', label: 'Configuration', icon: Settings },
              { id: 'tickets', label: 'Tickets', icon: Ticket }
            ].map(({ id, label, icon: Icon }) => (
              <button
                key={id}
                onClick={() => setActiveTab(id)}
                className={`flex items-center px-3 py-4 text-sm font-medium border-b-2 ${
                  activeTab === id
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700'
                }`}
              >
                <Icon className="mr-2" size={16} />
                {label}
              </button>
            ))}
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {activeTab === 'dashboard' && <Dashboard />}
        {activeTab === 'config' && (
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <ConfigurationForm />
            <ConfigurationList />
          </div>
        )}
        {activeTab === 'tickets' && <TicketManager />}
      </main>
    </div>
  );
};

export default TicketSystemApp;