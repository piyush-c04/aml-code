define(['ojs/ojarraydataprovider', 'models/data', 'viewModels/base', 'ojs/ojtable'], function (ArrayDataProvider, data, BaseViewModel) {
  'use strict';
  function TransactionsViewModel() {
    var self = this; BaseViewModel.call(self);
    self.rows = data.transactions; self.dataProvider = new ArrayDataProvider(self.rows, { keyAttributes: 'id' });
    self.columns = [
      {headerText:'TRANSACTION',field:'id'},{headerText:'CUSTOMER',field:'customer'},{headerText:'DATE',field:'date'},
      {headerText:'AMOUNT',field:'amount'},{headerText:'TYPE',field:'type'},{headerText:'CHANNEL',field:'channel'},
      {headerText:'BENEFICIARY',field:'beneficiary'},{headerText:'LOCATION',field:'location'},{headerText:'RISK',field:'score'},{headerText:'STATUS',field:'status'}
    ];
    self.screen = function(){window.alert('Transaction screening form ready for API submission.');};
  }
  return TransactionsViewModel;
});
