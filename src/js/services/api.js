define([], function () {
  'use strict';

  var API_BASE = 'http://localhost:8080/api/v1';
  var USE_DEMO_DATA = true;

  function request(path, options) {
    options = options || {};
    if (USE_DEMO_DATA) return Promise.resolve(null);

    var headers = options.headers || {};
    headers['Content-Type'] = 'application/json';
    headers.Authorization = 'Bearer ' + (window.localStorage.getItem('jwt_token') || '');

    return fetch(API_BASE + path, Object.assign({}, options, { headers: headers }))
      .then(function (response) {
        if (!response.ok) throw new Error('API request failed');
        return response.json();
      });
  }

  return {
    get: function (path) { return request(path, { method: 'GET' }); },
    post: function (path, body) {
      return request(path, { method: 'POST', body: JSON.stringify(body || {}) });
    }
  };
});
