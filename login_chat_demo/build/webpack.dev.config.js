//Create by : YueLi/xiroiyuki@hotmail.com 
//Create time: 2021/3/14 14:45
var path = require('path')
var webpack = require('webpack')
var webpackMerge = require('webpack-merge')
var baseConfig = require('./webpack.base.config')

module.exports = webpackMerge(baseConfig,{
  mode: 'development',
  module:{
    rules: [
      {
        test: /\.(css|styl|styl(us)?)$/,
        loader: 'style-loader!css-loader!postcss-loader',
      },
      {
        test: /\.(sass|scss)$/,
        loader: 'style-loader!css-loader!postcss-loader!sass-loader',

      },
      {
        test: /\.(svg|png|jpg|gif|ico)$/,
        //use: 'url-loader'
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 8192,
              name: '[name].[hash:8].[ext]',
            },
          }
        ]
      },
      {
        test: /\.(ttf|eot|woff|woff2)$/,
        use: [
          {
            loader: 'file-loader',
            options: {
              name: '[path][name].[ext]',
            },
          },
        ],
      },
      {
        test: /\.(m?js|js)$/i,
        exclude: /(node_modules|bower_components|)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env']
          }
        }
      }
    ]
  },
  devServer: {
    //contentBase: path.resolve(__dirname,'../dist'),
    inline: true,
    open:true,//自动打开页面
    hot:true,//开启模块热替换
    //告诉 dev-server 隐藏 webpack bundle 信息之类的消息。devServer.noInfo 默认禁用。
    noInfo: false,
  },
  plugins: [
    new webpack.NamedModulesPlugin(),//打印出更新位置
    new webpack.HotModuleReplacementPlugin(),//热替换
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"development"'
      }
    }),
  ],
  devtool:'source-map'
})