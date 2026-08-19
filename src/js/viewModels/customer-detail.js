define(['knockout','models/data','viewModels/base'], function(ko,data,BaseViewModel){
  'use strict';
  function CustomerDetailViewModel(params){var self=this;self.router=params&&params.rootRouter;BaseViewModel.call(self,self.router);var id=window.sessionStorage.getItem('aegis_selected_customer_id');self.customer=ko.observable(data.customers.find(function(c){return c.id===id;})||data.customers[0]);self.transactions=ko.observableArray(data.transactions.filter(function(transaction){return transaction.customer===self.customer().id;}));self.runAssessment=function(){var customer=self.customer();customer.last='Just now';customer.confidence=Math.min(99.8,customer.confidence+0.3);self.customer(customer);window.alert('AI assessment completed for '+customer.name);};self.back=function(){if(self.router)self.router.go('customers');};}
  return CustomerDetailViewModel;
});
