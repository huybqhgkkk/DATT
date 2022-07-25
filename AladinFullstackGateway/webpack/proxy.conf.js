function setupProxy() {
  const tls = process.env.TLS;
  const conf = [
    {
      context: [
        '/api/account',
        '/oauth2',
        '/login',
        '/auth',
        '/api',
        '/services',
        '/management',
        '/swagger-resources',
        '/v2/api-docs',
        '/v3/api-docs',
        '/h2-console',
        '/health',
      ],
      target: `http${tls ? 's' : ''}://localhost:21111`,
      secure: false,
      changeOrigin: tls,
      devServer: {
        allowedHosts: ['aladintech.co', 'www.aladintech.co'],
      },
    },
    // {
    //   context: ['/api', '/services', '/management', '/swagger-resources', '/v2/api-docs', '/v3/api-docs', '/h2-console', '/health'],
    //   target: `http${tls ? 's' : ''}://192.168.1.236:11111`,
    //   secure: false,
    //   changeOrigin: tls,
    //   devServer: {
    //     allowedHosts: ['aladintech.co', 'www.aladintech.co'],
    //   },
    // },
  ];
  return conf;
}

module.exports = setupProxy();
