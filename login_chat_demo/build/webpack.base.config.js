//Create by : YueLi/xiroiyuki@hotmail.com 
//Create time: 2021/3/14 14:40
var path = require('path')
var webpack = require('webpack')

const VueLoaderPlugin = require('vue-loader/lib/plugin')
const htmlWebpackPlugin = require('html-webpack-plugin')
const FriendWebpackErrorPlugin = require('friendly-errors-webpack-plugin')

module.exports={
  entry: path.resolve(__dirname,'../src/main.js'),
  output: {
    //publicPath: '../',
    path: path.resolve(__dirname,'../dist'),
    filename: './js/bundle.js'
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
            // Since sass-loader (weirdly) has SCSS as its default parse mode, we map
            // the "scss" and "sass" values for the lang attribute to the right configs here.
            // other preprocessors should work out of the box, no loader config like this necessary.
            'css': 'style-loader!css-loader!postcss-loader',
            'scss': 'style-loader!css-loader!sass-loader',
            'sass': 'style-loader!css-loader!sass-loader?indentedSyntax'
          }
          // other vue-loader options go here
        }
      },
      /*{
        /!*使用html-loader会导致html-webpack-plugin不会用ejs语法*!/
        /!*https://github.com/jantimon/html-webpack-plugin/blob/main/docs/template-option.md*!/
        test: /\.html$/i,
        loader: 'html-loader',
      },*/
      {
        test: /\.html$/i,
        use: {
          loader: 'html-loader',
          options: {
            //attrs: [':data-src']
          }
        }
      },
    ]
  },
  devServer: {
    //假设服务器运行在 http://localhost:8080 并且 output.filename 被设置为 bundle.js。默认 devServer.publicPath 是 '/'，所以你的包(bundle)可以通过 http://localhost:8080/bundle.js 访问。
    //publicPath: path.resolve(__dirname,'../dist/public'),
    //告诉服务器从哪个目录中提供内容。只有在你想要提供静态文件时才需要。devServer.publicPath 将用于确定应该从哪里提供 bundle，并且此选项优先。
    contentBase: [
      path.resolve(__dirname,'./js'),
      path.resolve(__dirname,'./css'),
      path.resolve(__dirname,'./images'),
    ],
    // inline: true,
    //当使用 HTML5 History API 时，任意的 404 响应都可能需要被替代为 index.html。devServer.historyApiFallback 默认禁用。
    historyApiFallback: true,
    //告诉 dev-server 隐藏 webpack bundle 信息之类的消息。devServer.noInfo 默认禁用。
    //noInfo: true,
    proxy:{//跨域请求
      '/backendServer': {
        target: 'http://localhost:8081',
        pathRewrite: {'^/backendServer': ''},
        changeOrigin: true,
        secure: false
      },
      '/ws': {
        target: 'ws://localhost:8081',
        ws: true,
        secure: false,
      }
    }
  },
  plugins: [
    new VueLoaderPlugin(),
    new htmlWebpackPlugin({
      //publicPath: '/',
      title: 'Webpack App',
      favicon: path.resolve(__dirname,'../public/favicon.ico'),
      filename: 'index.html',
      template: path.resolve(__dirname,'../public/index.html'),
      //是否生成hash添加在引入文件地址的末尾，这个可以避免缓存带来的麻烦。默认为true
      hash: true,
      //默认是true的，表示内容变化的时候生成一个新的文件。
      cache: true,
      // minify：使用minify会对生成的html文件进行压缩，默认是false。html-webpack-plugin内部集成了html-minifier，因此，还可以对minify进行配置，常用的配置项是：
      // caseSensitive:false,//是否大小写敏感
      // collapseWhitespace:true//是否去除空格
      // removeAttributeQuotes:true// 去掉属性引用
      // removeComments:true,//去注释
      minify: {
        collapseWhitespace: false,//
        caseSensitive: true,
        removeComments: true,
      },
      //inject：引入模板的注入位置，取值有true/false/body/head
      //true：默认值，script标签位于html文件的body底部
      // body:script标签位于html文件的body底部
      // head:script标签位于html文件的head中
      // false:不插入生成的js文件，这个几乎不会用到的
      inject: true,
      // showErrors:：是否将错误信息写在页面里，默认true，出现错误信息则会包裹在一个pre标签内添加到页 面上。
      // chunks:：引入的模块，这里指定的是entry中设置多个js时，在这里指定引入的js，如果不设置则默认全部引入。
    }),
    new FriendWebpackErrorPlugin()

  ],
  node: {
    //解决net报错
    net: 'empty',
  },
  //文件扩展名extensions和别名alias
  resolve: {
    extensions: ['.wasm', '.mjs', '.js', '.json','.html','.vue','.css','.png','.jpg','.jpeg','.ico','.ttf','.woff'],
    alias: {
      'vue$': 'vue/dist/vue.common.js'
    },
  },

}