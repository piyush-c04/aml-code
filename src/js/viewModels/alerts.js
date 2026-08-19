define(['knockout','ojs/ojarraydataprovider','models/data','viewModels/base','ojs/ojtable'], function (ko, ArrayDataProvider, data, BaseViewModel) {
  'use strict';
  function AlertsViewModel(){
    var self=this; BaseViewModel.call(self); self.severity=ko.observable('All severities'); self.status=ko.observable('All statuses'); self.rows=ko.observableArray(data.alerts.slice());
    self.filtered=ko.computed(function(){return self.rows().filter(function(a){return (self.severity()==='All severities'||a.severity===self.severity())&&(self.status()==='All statuses'||a.status===self.status());});});
    self.dataProvider=ko.pureComputed(function(){return new ArrayDataProvider(self.filtered(),{keyAttributes:'id'});});
    self.columns=[{headerText:'ALERT',field:'id'},{headerText:'CUSTOMER',field:'customer'},{headerText:'TRANSACTION',field:'txn'},{headerText:'TYPE',field:'type'},{headerText:'SEVERITY',field:'severity'},{headerText:'SCORE',field:'score'},{headerText:'STATUS',field:'status'},{headerText:'ASSIGNED',field:'analyst'},{headerText:'CREATED',field:'created'}];
    self.selectedAlert=ko.observable(null);
    self.selectedCustomer=ko.pureComputed(function(){var alert=self.selectedAlert();return alert&&data.customers.find(function(customer){return customer.id===alert.customer;});});
    self.openAlert=function(alert){self.selectedAlert(alert||null);};
    self.closeAlert=function(){self.selectedAlert(null);};
    self.markFalsePositive=function(){var alert=self.selectedAlert();if(alert){alert.status='RESOLVED';self.rows.valueHasMutated();self.closeAlert();}};
    self.assignAnalyst=function(){var alert=self.selectedAlert();if(alert){alert.analyst='J. Doshi';self.rows.valueHasMutated();}};
    self.createInvestigation=function(){var alert=self.selectedAlert();if(alert){alert.status='ESCALATED';self.rows.valueHasMutated();window.alert('Investigation created for '+alert.id);self.closeAlert();}};
  } return AlertsViewModel;
});
