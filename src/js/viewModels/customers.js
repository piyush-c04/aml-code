define([
  'knockout',
  'ojs/ojarraydataprovider',
  'models/data',
  'viewModels/base',
  'ojs/ojtable'
], function (ko, ArrayDataProvider, data, BaseViewModel) {
  'use strict';

  function CustomersViewModel(params) {
    var self = this;
    self.router = params && params.rootRouter;
    BaseViewModel.call(self, self.router);
    self.search = ko.observable('');
    self.customers = ko.observableArray(data.customers.slice());
    self.filteredCustomers = ko.computed(function () {
      var q = self.search().toLowerCase();
      return self.customers().filter(function (c) {
        return (c.id + ' ' + c.name + ' ' + c.risk).toLowerCase().indexOf(q) >= 0;
      });
    });
    self.dataProvider = ko.pureComputed(function () {
      return new ArrayDataProvider(self.filteredCustomers(), { keyAttributes: 'id' });
    });
    self.columns = [
      { headerText: 'CUSTOMER', field: 'name' },
      { headerText: 'RISK SCORE', field: 'score' },
      { headerText: 'CATEGORY', field: 'risk' },
      { headerText: 'ACCOUNTS', field: 'accounts' },
      { headerText: 'TRANSACTIONS', field: 'transactions' },
      { headerText: 'ALERTS', field: 'alerts' },
      { headerText: 'LAST ASSESSMENT', field: 'last' }
    ];
    self.addCustomer = function () { window.alert('Add customer form ready.'); };
    self.openCustomer = function (customer) {
      if (!customer || !self.router) return;
      window.sessionStorage.setItem('aegis_selected_customer_id', customer.id);
      self.router.go('customer-detail');
    };
  }
  return CustomersViewModel;
});
