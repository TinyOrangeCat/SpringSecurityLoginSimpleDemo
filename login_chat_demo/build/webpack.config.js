//Create by : YueLi/xiroiyuki@hotmail.com 
//Create time: 2021/3/11 18:44
var path = require('path')
var webpack = require('webpack')

const VueLoaderPlugin = require('vue-loader/lib/plugin')
const htmlWebpackPlugin = require("html-webpack-plugin")
const UglifyJsPlugin = require('uglifyjs-webpack-plugin')
const {CleanWebpackPlugin} = require('clean-webpack-plugin')
const FriendWebpackErrorPlugin = require('friendly-errors-webpack-plugin')
/**
 * 提取css到单独文件
 * npm install --save-dev mini-css-extract-plugin
 */
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
/**
 * css 压缩 会清除css中注释
 * npm install --save-dev optimize-css-assets-webpack-plugin
 */
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");


module.exports={
  entry: path.resolve(__dirname,'../src/main.js'),
  output: {
    path: path.resolve(__dirname,'../dist'),
    //publicPath: '/asserts/',
    filename: 'bundle.js',
  },
  optimization: {
    //打包 第三方库
    //打包 公共文件
    splitChunks: {
      cacheGroups: {
        vendor:{//node_modules内的依赖库
          chunks:"all",
          test: /([\\/]node_modules[\\/])/,
          name:"vendor",
          minChunks: 1, //被不同entry引用次数(import),1次的话没必要提取
          maxInitialRequests: 5,
          minSize: 3000,
          priority:100,
          // enforce: true?
        }
      }
    },
    //这个webpack中专用的术语用于管理webpack内部的打包进程。bundle由许多chunk组成，chunk有几种类型，比如说“入口”和“子块”。通常chunk和输出的bundle一一对应，但是，有些是一对多的关系。
    runtimeChunk: {
      name: 'manifest'
    },
    minimizer: [
      new UglifyJsPlugin({
        cache: true,
        parallel: true,
        /**
         *  sourceMap 和 devtool: 'inline-source-map', 冲突
         */
        sourceMap: false, // set to true if you want JS source maps,
        /**
         *  extractComments 导出备注
         */
        extractComments: 'all'
      }),
      new OptimizeCSSAssetsPlugin({})
    ]
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
            /*'scss': 'vue-style-loader!css-loader!sass-loader',
            'sass': 'vue-style-loader!css-loader!sass-loader?indentedSyntax'*/
            'css': 'style-loader!css-loader!postcss-loader',
            'scss': 'style-loader!css-loader!sass-loader',
            'sass': 'style-loader!css-loader!sass-loader?indentedSyntax'
          }
          // other vue-loader options go here
        }
      },
      {
        test: /\.(css|styl|styl(us)?)$/,
        //loader: 'style-loader!css-loader',
        //use: ['style-loader','css-loader','stylus-loader','postcss-loader','sass-loader'],
        //loader: 'style-loader!css-loader!postcss-loader',
        //use: [{loader: MiniCssExtractPlugin.loader},'style-loader!css-loader!postcss-loader']
        use: [{loader: MiniCssExtractPlugin.loader},'css-loader']
        /*options: {
          name: '[name].[ext]?[hash]'
        }*/
      },
      {
        test: /\.(sass|scss)$/,
        loader: 'style-loader!css-loader!postcss-loader!sass-loader',
        /*options: {
          name: '[name].[ext]?[hash]'
        }*/
      },
      /*{
        test: /\.(png|jpg|gif)$/,
        loader: 'file-loader',
        options: {
          name: '[name].[ext]?[hash]',
          limit: 8522,
          //outputPath: './asserts/images'
        }
      },*/
      {
        test: /\.(ttf|eot|svg|woff|woff2|png|jpg|gif)$/,
        //use: 'url-loader'
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 8480,
              //name: 'asserts/images/[name}.[hash:8}.[ext]'
            },

          }
        ]
      },
      {
        /*使用html-loader会导致html-webpack-plugin不会用ejs语法*/
        /*https://github.com/jantimon/html-webpack-plugin/blob/main/docs/template-option.md*/
        test: /\.html$/i,
        loader: 'html-loader',
      },
      {
        test: /\.(m?js|js)$/,
        exclude: /(node_modules[\\\/]webpack[\\\/]buildin|node_modules[\\\/]core-js)|node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env'],
          }
        }
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    new CleanWebpackPlugin(),
    new htmlWebpackPlugin({
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
    new MiniCssExtractPlugin({
      filename: "[name].css",
      chunkFilename: "[id].css"
    }),

    /**
     *  提取SourceMap到独立文件
     */
    new webpack.SourceMapDevToolPlugin({
      filename: '[name].js.map',
    }),
    new FriendWebpackErrorPlugin()

  ],
  resolve: {
    extensions: ['.wasm', '.mjs', '.js', '.json','.html','.vue','.css'],
    alias: {
      'vue$': 'vue/dist/vue.common.js'
    },
    /*modules: [
      'node_modules'
    ]*/
    //extensions: ['.wasm', '.mjs', '.js', '.json','.html',]
  },
  devServer: {
    contentBase: './dist',
    inline: true,
    historyApiFallback: true,
    noInfo: true,
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
  performance: {
    hints: false
  },
  devtool: '#eval-source-map',
  stats: {
// One of the two if I remember right
    //entrypoints: false,
    //children: false
  },
  //将webpack不能打包的部分排除在外
  /*externals: {
    //net: '@type',
    lodash: {
      commonjs: 'lodash',
      commonjs2: 'lodash',
      amd: 'lodash',
      root: '_'
    },
    /!*fallback: {
      net: false
    }*!/
  },*/
  node: {
    net: 'empty'
  }
}
if(process.env.NODE_ENV === 'production'){
  module.exports.devtool = '#source-map'
  // http://vue-loader.vuejs.org/en/workflow/production.html
  module.exports.plugins = (module.exports.plugins || []).concat([
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    //depareted
    /*new webpack.optimize.UglifyJsPlugin({
      sourceMap: true,
      compress: {
        warnings: false
      }
    }),*/
    new webpack.LoaderOptionsPlugin({
      minimize: true
    })
  ])
  /*module.exports.externals = {
    stompjs: 'net'
  }*/
}
