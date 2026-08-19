define([
  'knockout',
  'ojs/ojarraydataprovider',
  'models/data',
  'viewModels/base',
  'ojs/ojtable'
], function (ko, ArrayDataProvider, data, BaseViewModel) {
  'use strict';

  function DashboardViewModel() {
    var self = this;
    BaseViewModel.call(self);

    self.metrics = [
      ['Total customers', '7', '100% KYC coverage', 'danger-none'],
      ['Transactions monitored', '8,436', 'Up 12.4% this month', 'danger-none'],
      ['Open alerts', '9', '3 critical alerts', 'warn'],
      ['Open investigations', '5', '1 SLA due today', 'warn'],
      ['Active accounts', '12', 'All monitored', 'danger-none'],
      ['Suspicious transactions', '31', '0.37% detection rate', 'warn'],
      ['High-risk customers', '3', '1 newly classified', 'warn'],
      ['AI confidence', '94.2%', 'Up 1.8% model quality', 'danger-none']
    ];

    self.trend = data.trend;
    self.alerts = data.alerts.slice(0, 4);
    self.alertsDataProvider = new ArrayDataProvider(self.alerts, { keyAttributes: 'id' });

    self.alertColumns = [
      { headerText: 'ALERT', field: 'id' },
      { headerText: 'CUSTOMER', field: 'customer' },
      { headerText: 'TRANSACTION', field: 'txn' },
      { headerText: 'TYPE', field: 'type' },
      { headerText: 'SEVERITY', field: 'severity' },
      { headerText: 'SCORE', field: 'score' },
      { headerText: 'STATUS', field: 'status' },
      { headerText: 'ASSIGNED', field: 'analyst' },
      { headerText: 'CREATED', field: 'created' }
    ];

    self.openAlert = function (event) {
      var key = event.detail && event.detail.context && event.detail.context.key;
      if (key) window.alert('Open alert ' + key);
    };

    self.screenTransaction = function () {
      window.alert('Transaction screening form ready for API submission.');
    };

    self.exportSummary = function () {
      window.alert('Summary export started.');
    };
  }

  return DashboardViewModel;
});
