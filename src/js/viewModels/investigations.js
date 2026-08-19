define(['knockout','ojs/ojarraydataprovider','models/data','viewModels/base','ojs/ojtable'], function(ko,ArrayDataProvider,data,BaseViewModel){
  'use strict';
  function InvestigationsViewModel(){
    var self=this;
    BaseViewModel.call(self);
    self.rows=ko.observableArray(data.investigations.slice());
    self.dataProvider=new ArrayDataProvider(self.rows,{keyAttributes:'id'});
    self.columns=[{headerText:'INVESTIGATION',field:'id'},{headerText:'SOURCE ALERT',field:'alert'},{headerText:'CUSTOMER',field:'customer'},{headerText:'INVESTIGATOR',field:'owner'},{headerText:'PRIORITY',field:'priority'},{headerText:'STATUS',field:'status'},{headerText:'CREATED',field:'created'},{headerText:'DUE DATE',field:'due'},{headerText:'RESOLUTION',field:'resolution'}];
    self.selectedInvestigation=ko.observable(null);
    self.noteDraft=ko.observable('');
    self.timeline=ko.observableArray([]);
    self.selectedCustomer=ko.pureComputed(function(){var item=self.selectedInvestigation();return item&&data.customers.find(function(customer){return customer.id===item.customer;});});
    self.priorityClass=function(priority){return String(priority).toLowerCase();};
    self.statusClass=function(status){return String(status).toLowerCase().replace(/\\s+/g,'-');};
    self.openInvestigation=function(investigation){
      self.selectedInvestigation(investigation||null);
      self.noteDraft('');
      self.timeline([{title:'Alert created',detail:'Kafka · aml.alert.created'},{title:'Investigation opened',detail:'Correlation ID retained'},{title:'Customer profile reviewed',detail:'Audit entry recorded'}]);
    };
    self.closeInvestigation=function(){self.selectedInvestigation(null);};
    self.addNote=function(){var note=self.noteDraft().trim();if(!note)return;self.timeline.push({title:'Analyst note added',detail:note});self.noteDraft('');};
    self.escalate=function(){var item=self.selectedInvestigation();if(!item)return;item.status='ESCALATED';self.rows.valueHasMutated();self.timeline.push({title:'Case escalated',detail:'Escalated for senior review'});};
    self.resolve=function(){var item=self.selectedInvestigation();if(!item)return;item.status='RESOLVED';item.resolution='RESOLVED';self.rows.valueHasMutated();self.timeline.push({title:'Case resolved',detail:'Resolution recorded'});};
    self.create=function(){window.alert('Create investigation form ready.');};
  }
  return InvestigationsViewModel;
});
