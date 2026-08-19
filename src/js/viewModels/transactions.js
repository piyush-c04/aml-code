define(['knockout', 'ojs/ojarraydataprovider', 'services/api', 'viewModels/base', 'ojs/ojtable'], function (ko, ArrayDataProvider, api, BaseViewModel) {
  'use strict';
  function TransactionsViewModel() {
    var self = this; BaseViewModel.call(self);
    self.rows = ko.observableArray([]); self.error = ko.observable(''); self.loading = ko.observable(true);
    self.dataProvider = ko.pureComputed(function () { return new ArrayDataProvider(self.rows(), { keyAttributes: 'id' }); });
    self.columns = [
      {headerText:'TRANSACTION ID',field:'transactionId'},{headerText:'SENDER ACCOUNT',field:'senderAccountId'},{headerText:'RECEIVER ACCOUNT',field:'receiverAccountId'},
      {headerText:'DATE',field:'transactionDatetime'},{headerText:'AMOUNT',field:'amount'},{headerText:'PAYMENT CURRENCY',field:'paymentCurrency'},
      {headerText:'RECEIVED CURRENCY',field:'receivedCurrency'},{headerText:'PAYMENT TYPE',field:'paymentType'},{headerText:'STATUS',field:'status'}
    ];
    self.screen = function(){window.alert('Transaction screening form ready for API submission.');};
    api.get('/transactions').then(function (items) {
      self.rows((items || []).map(function (transaction) {
        transaction.transactionDatetime = transaction.transactionDatetime ? new Date(transaction.transactionDatetime).toLocaleString() : '';
        return transaction;
      }));
    }).catch(function (error) { self.error(error.message); }).finally(function () { self.loading(false); });
  }
  return TransactionsViewModel;
});
