//Create by : YueLi/xiroiyuki@hotmail.com 
//Create time: 2021/3/14 14:45
var path = require('path')
var webpack = require('webpack')
var webpackMerge = require('webpack-merge')
var baseConfig = require('./webpack.base.config')

const {CleanWebpackPlugin} = require('clean-webpack-plugin')
/**
 * 提取css到单独文件
 * npm install --save-dev mini-css-extract-plugin
 */
const MiniCssExtractPlugin = require("mini-css-extract-plugin")
/**
 * css 压缩 会清除css中注释
 * npm install --save-dev optimize-css-assets-webpack-plugin
 */
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin")
/**
 * js 压缩  webpack4自带uglifyjs
 * npm install uglifyjs-webpack-plugin --save-dev
 */
const UglifyJsPlugin = require("uglifyjs-webpack-plugin");

module.exports = webpackMerge(baseConfig,{
  mode: 'production',
  module:{
    rules: [
      {
        //https://blog.csdn.net/qq_37431724/article/details/90343899
        test: /\.(css|sass|scss|styl|styl(us)?)$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: {
              publicPath: '../'
            }
          },
          {
            loader: 'css-loader',
            options: {
              importLoaders: 2,
              sourceMap: true,
              //publicPath: '../'
            }
          },
          {
            loader: 'postcss-loader',
            options: {
              postcssOptions:{
                ident:"postcss",
                plugins:[
                  require("autoprefixer")()
                ]
              },

              /*plugins: () => [
                require('autoprefixer')
              ],*/
              sourceMap: true,
              //publicPath: '../'
            }
          },
          {
            loader: 'sass-loader',
            options: {
              sourceMap: true,
              //publicPath: '../'
            }
          }
        ],
        exclude: /node_modules/,
      },
      /*{
        test: /\.(css|sass|scss|styl|styl(us)?)$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: {
              // you can specify a publicPath here
              // by default it use publicPath in webpackOptions.output
              //如果MiniCssExtractPlugin的filename中配置了路径，此处一定要这样配，否则可能会找不到内部引用的ttf,woff文件
              //这是因为我们在使用MiniCSSExtractPlugin的时候，把打包好的文件放到一个新的文件夹中。新建了一个css文件夹，所以才会多了一级目录。
              publicPath: '../'
            }
          },
          "css-loader"
        ]
      },*/
      /*{
        test: /\.scss$/,
        use: [{
          loader: "style-loader" // 将 JS 字符串生成为 style 节点
        }, {
          loader: "css-loader" // 将 CSS 转化成 CommonJS 模块
        }, {
          loader: "sass-loader", // 将 Sass 编译成 CSS
          options: {
            name: '[name].[hash:8].[ext]',
            outputPath: './css'
          }
        }]
      },*/
      {
        test: /\.(svg|png|jpg|gif|ico)$/,
        //use: 'url-loader'
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 8192,
              name: '[name].[hash:8].[ext]',
              outputPath: './images'
            },
          }
        ]
      },
      {
        test: /\.(ttf|woff)$/,
        //use: 'url-loader'
        include: path.resolve(__dirname,'../src/themes/green_theme/fonts'),
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 8192,
              name: '[name].[hash:8].[ext]',
              outputPath: './fonts'
            },
          }
        ]
      },
      {
        test: /\.(eot|woff2|txt)$/,
        use: [
          {
            loader: 'file-loader',
            options: {
              //limit: 10240,
              name: '[name].[hash:8].[ext]',
              outputPath: './files'
            },
          }
        ]
      },
      {
        test: /\.(m?js|js)$/,
        exclude: /(node_modules|bower_components)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env'],
          }
        }
      }
    ]
  },
  devServer: {
    //contentBase: './dist',
    inline: false,
    //告诉 dev-server 隐藏 webpack bundle 信息之类的消息。devServer.noInfo 默认禁用。
    noInfo: true,
  },
  plugins: [
    //由 webpack 生成的文件或目录才能被 CleanWebpackPlugin 删除
    new CleanWebpackPlugin(),
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    new MiniCssExtractPlugin({
      filename: './css/[name].[hash:8].css',
      chunkFilename: './css/chunk[id].[hash:8].css',
    }),
    /**
     *  提取SourceMap到独立文件
     */
    new webpack.SourceMapDevToolPlugin({
      filename: './js/[name].js.map',
      // exclude: ['vendor.js']
    })
  ],
  optimization: {
    /**
     * 分包
     */
    splitChunks: {
      chunks: 'all',
      minSize: 30000,
      maxSize: 0,
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
  }

})