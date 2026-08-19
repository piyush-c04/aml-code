define([], function () {
  'use strict';

  // The OJET dev server proxies /api to the Spring gateway, avoiding browser CORS.
  var API_BASE = window.localStorage.getItem('aml_api_base') || '/api/v1';

  function request(path, options) {
    options = options || {};
    var headers = options.headers || {};
    if (options.body) headers['Content-Type'] = 'application/json';
    var token = window.localStorage.getItem('jwt_token');
    if (token) headers.Authorization = 'Bearer ' + token;

    return fetch(API_BASE + path, Object.assign({}, options, { headers: headers }))
      .then(function (response) {
        return response.json().catch(function () { return {}; }).then(function (payload) {
          if (!response.ok || payload.success === false) {
            throw new Error(payload.message || ('API request failed (' + response.status + ')'));
          }
          // Spring's ApiResponse wraps every controller payload in `data`.
          return Object.prototype.hasOwnProperty.call(payload, 'data') ? payload.data : payload;
        });
      });
  }

  return {
    get: function (path) { return request(path, { method: 'GET' }); },
    post: function (path, body) {
      return request(path, { method: 'POST', body: JSON.stringify(body || {}) });
    },
    put: function (path, body) { return request(path, { method: 'PUT', body: JSON.stringify(body || {}) }); },
    remove: function (path) { return request(path, { method: 'DELETE' }); }
  };
});
