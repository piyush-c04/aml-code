/**
 * Development-only proxy. The browser calls the same OJET origin (/api), and
 * webpack-dev-server forwards requests to the Spring gateway on port 8080.
 */
module.exports = {
  webpack: ({ context, config }) => {
    if (context.buildType !== 'release') {
      config.devServer.proxy = [
        {
          context: ['/api'],
          target: 'http://localhost:8080',
          changeOrigin: true
        }
      ];
    }
    return { context, webpack: config };
  }
};
