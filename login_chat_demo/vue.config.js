//Create by : YueLi/xiroiyuki@hotmail.com 
//Create time: 2021/2/27 20:05

//配置跨域请求
//代理对象
let proxyObj={};
//代理WebSocket
proxyObj['/ws']={
    ws: true,
    //以ws开头！！！
    target: 'ws://localhost:8081',
}
//代理所有路径
proxyObj['/backendServer']={
    //websocket
    ws: false,
    //代理目标地址
    target: 'http://localhost:8081',
    //发送请求头时，host会更改为target
    changeOrigin: true,
    //不重写地址
    pathRewrite:{
        '^/backendServer':''
    }
}

module.exports={
    devServer:{
        host: 'localhost',
        port: 8080,
        /*proxy: {
            '/backendServer': {
                ws: false,
                //代理目标地址
                target: 'http://localhost:8081',
                //发送请求头时，host会更改为target
                changeOrigin: true,
                //不重写地址
                pathRewrite:{
                    '^/backendServer':''
                }
            },
            '/ws': {
                ws: true,
                //以ws开头！！！
                target: 'ws://localhost:8081',
            }
        }*/
        proxy: proxyObj
    },

}