define([], function () {
  'use strict';
  return {
    customers: [
      { id:'CUS-1001', name:'Stuti Agrawal', score:91, risk:'CRITICAL', accounts:2, transactions:1482, alerts:4, last:'17 Aug 2026', kyc:'VERIFIED', type:'INDIVIDUAL', country:'India', occupation:'Business owner', onboarded:'12 Mar 2021', confidence:95.2 },
      { id:'CUS-1002', name:'Astha Singh', score:76, risk:'HIGH', accounts:1, transactions:934, alerts:2, last:'17 Aug 2026', kyc:'VERIFIED', type:'INDIVIDUAL', country:'India', occupation:'Consultant', onboarded:'08 Jul 2022', confidence:89.8 },
      { id:'CUS-1003', name:'Deev Savla', score:28, risk:'LOW', accounts:3, transactions:1108, alerts:0, last:'16 Aug 2026', kyc:'VERIFIED', type:'BUSINESS', country:'India', occupation:'Director', onboarded:'18 Jan 2020', confidence:92.1 },
      { id:'CUS-1004', name:'Piyush Chaudhari', score:63, risk:'MEDIUM', accounts:2, transactions:762, alerts:1, last:'16 Aug 2026', kyc:'PENDING', type:'INDIVIDUAL', country:'UAE', occupation:'Engineer', onboarded:'25 Nov 2023', confidence:86.4 },
      { id:'CUS-1005', name:'Saksham Negi', score:84, risk:'HIGH', accounts:2, transactions:1391, alerts:3, last:'15 Aug 2026', kyc:'VERIFIED', type:'BUSINESS', country:'Hong Kong', occupation:'Trader', onboarded:'14 May 2021', confidence:93.6 },
      { id:'CUS-1006', name:'Jeel Doshi', score:18, risk:'LOW', accounts:1, transactions:688, alerts:0, last:'15 Aug 2026', kyc:'VERIFIED', type:'INDIVIDUAL', country:'India', occupation:'Developer', onboarded:'04 Apr 2024', confidence:97.1 },
      { id:'CUS-1007', name:'Naman Kanojia', score:52, risk:'MEDIUM', accounts:1, transactions:571, alerts:1, last:'14 Aug 2026', kyc:'REVIEW', type:'INDIVIDUAL', country:'India', occupation:'Analyst', onboarded:'03 Feb 2023', confidence:81.9 }
    ],
    transactions: [
      {id:'TXN-8F91A2',customer:'CUS-1001',date:'17 Aug, 12:42',amount:84250,currency:'USD',type:'WIRE',channel:'ONLINE',beneficiary:'BEN-8821',location:'Iran',score:94,status:'REVIEW_REQUIRED'},
      {id:'TXN-7C14B8',customer:'CUS-1005',date:'17 Aug, 11:18',amount:48750,currency:'USD',type:'TRANSFER',channel:'MOBILE',beneficiary:'BEN-1068',location:'UAE',score:87,status:'FLAGGED'},
      {id:'TXN-4D22C7',customer:'CUS-1002',date:'17 Aug, 09:37',amount:22300,currency:'EUR',type:'WIRE',channel:'BRANCH',beneficiary:'BEN-7334',location:'Hong Kong',score:78,status:'FLAGGED'},
      {id:'TXN-9A33F1',customer:'CUS-1004',date:'16 Aug, 22:11',amount:9800,currency:'USD',type:'TRANSFER',channel:'ONLINE',beneficiary:'BEN-5510',location:'Pakistan',score:66,status:'MONITORING'},
      {id:'TXN-2E65D4',customer:'CUS-1007',date:'16 Aug, 18:04',amount:15600,currency:'GBP',type:'WIRE',channel:'ONLINE',beneficiary:'BEN-3302',location:'United Kingdom',score:59,status:'MONITORING'}
    ],
    alerts: [
      {id:'ALT-2048',customer:'CUS-1001',txn:'TXN-8F91A2',type:'GEOGRAPHIC ANOMALY',severity:'CRITICAL',score:94,status:'NEW',analyst:'Unassigned',created:'17 Aug, 12:43'},
      {id:'ALT-2047',customer:'CUS-1005',txn:'TXN-7C14B8',type:'STRUCTURING',severity:'CRITICAL',score:87,status:'UNDER REVIEW',analyst:'A. Mehta',created:'17 Aug, 11:19'},
      {id:'ALT-2044',customer:'CUS-1002',txn:'TXN-4D22C7',type:'RAPID FUND MOVEMENT',severity:'HIGH',score:78,status:'ESCALATED',analyst:'J. Doshi',created:'17 Aug, 09:38'},
      {id:'ALT-2040',customer:'CUS-1004',txn:'TXN-9A33F1',type:'VELOCITY',severity:'MEDIUM',score:66,status:'UNDER REVIEW',analyst:'S. Shah',created:'16 Aug, 22:12'},
      {id:'ALT-2037',customer:'CUS-1007',txn:'TXN-2E65D4',type:'HIGH VALUE',severity:'MEDIUM',score:59,status:'RESOLVED',analyst:'J. Doshi',created:'16 Aug, 18:05'}
    ],
    investigations: [
      {id:'INV-1092',alert:'ALT-2048',customer:'CUS-1001',owner:'Unassigned',priority:'CRITICAL',status:'OPEN',created:'17 Aug 2026',due:'18 Aug 2026',resolution:'-'},
      {id:'INV-1091',alert:'ALT-2047',customer:'CUS-1005',owner:'A. Mehta',priority:'CRITICAL',status:'INVESTIGATING',created:'17 Aug 2026',due:'19 Aug 2026',resolution:'-'},
      {id:'INV-1089',alert:'ALT-2044',customer:'CUS-1002',owner:'J. Doshi',priority:'HIGH',status:'ESCALATED',created:'17 Aug 2026',due:'20 Aug 2026',resolution:'-'},
      {id:'INV-1084',alert:'ALT-2037',customer:'CUS-1007',owner:'J. Doshi',priority:'MEDIUM',status:'RESOLVED',created:'16 Aug 2026',due:'21 Aug 2026',resolution:'FALSE POSITIVE'}
    ],
    rules: [
      ['RULE-001','High-value transaction','Amount exceeds configured threshold','USD 10,000','HIGH',18],
      ['RULE-002','Transaction velocity','More than N transactions within M minutes','20 / 60 min','HIGH',9],
      ['RULE-003','Rapid fund movement','Large incoming followed by outgoing transfer','80% / 2 hr','CRITICAL',6],
      ['RULE-004','New beneficiary','Large transfer to recently added beneficiary','7 days','MEDIUM',13],
      ['RULE-005','Geographic anomaly','Location differs from customer history','Risk >= 70','HIGH',7],
      ['RULE-006','Structuring','Repeated transfers near reporting threshold','5 / 24 hr','CRITICAL',11]
    ],
    trend:[38,55,44,61,85,52,72,48,79,58,67,91,63,76]
  };
});
