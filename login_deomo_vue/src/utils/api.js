//Create by : YueLi/xiroiyuki@hotmail.com 
//Create time: 2021/2/25 22:09
import axios from 'axios'
import { Message } from 'element-ui'
import router from '@/router'
//import store from "@/store"
import qs from 'qs'

function getNewUrl(urlArray){
    let resultUrl = '';
    for(let index in urlArray){
        let urlItem = urlArray[index];
        console.log("urlItem: "+urlItem);
        if(urlItem != '' && urlItem != 'user' && urlItem !='manager'){
            resultUrl = resultUrl+'/'+urlItem;
        }
    }
    return resultUrl;
}

axios.interceptors.request.use(config=>{
    let requestUrl = config.url;
    let urls = requestUrl.split('/');
        console.log("urls: "+JSON.stringify(urls));

    if(urls.length > 3 && 'user'==urls[2]){
        const userToken = window.sessionStorage.getItem('userToken');
        console.log("userToken " +userToken);
        if(userToken){
            config.headers['Authorization'] = userToken;
            console.log("add token in user request");
        }
        /*if(store.getters.getUserTokenInfo && store.getters.getUserTokenInfo != ''){
            config.headers['Authorization'] = store.getters.getUserTokenInfo;
            console.log("add token in user request");
        }*/

        config.url = getNewUrl(urls);
    }else if(urls.length > 3 && 'manager'==urls[2]){
        const managerToken = window.sessionStorage.getItem('managerToken');
        console.log("managerToken " +managerToken);
        if(managerToken){
            config.headers['Authorization'] = managerToken;
            console.log("add token in manager request");
        }
        /*if(store.getters.getManagerTokenInfo && store.getters.getManagerTokenInfo != ''){
            config.headers['Authorization'] = store.getters.getManagerTokenInfo;
            console.log("add token in manager request");
        }*/
        config.url = getNewUrl(urls);
    }
    console.log("config headers:"+JSON.stringify(config.headers));
    //console.log("token: "+store.getters.getUserTokenInfo)
    console.log("final url : "+config.url);
    return config;
},onReject=>{

})

axios.interceptors.response.use(success=>{
    if(success.status && success.status == 200){
        //响应成功，但后端返回失败码
        if(success.data.code == 404 || success.data.code == 401 || success.data.code == 403 || success.data.code == 500){
            Message.error(success.data.message);
            return;
        }
        if(success.data.message){
            Message.success(success.data.message);
        }
        return success.data;
    }
},error =>{//与服务器失联
    //Message.error("错误");
    console.log(error.message);
    if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        if(error.response.status){
            if(error.response.status == 504 || error.response.status == 404 || error.response.status == 500){
                Message.error({message:'与服务器失联惹≡(▔﹏▔)≡'});
            }else if(error.response.status == 403){
                Message.error({message:'权限不足,请联系管理员＞﹏＜'});
            }else if(error.response.status == 401){
                Message.error({message:'尚未登录，请登录/(ㄒoㄒ)/~~'});
                router.replace('/')
            }else {
                Message.error({message:'出错了:'+error.response.status+' '+error.message});
                //Message.error({message:'இ௰இ 出错了:'+error.message});
            }
        }else if(error.response.data){
            Message.error({message:error.response.data});
        }else if(error.response.headers){
            Message.error({message:error.response.headers});
        }
    } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        //console.log("error.request "+error.request);
        Message.error({message:error.request});
    } else {
        if (error.message) {
        // Something happened in setting up the request that triggered an Error
            Message.error({message:error.message});
            console.log(error.message);
        }else{
            Message.error({message:'发生未知错误⊙﹏⊙∥'});
        }
    }

})

let base = ''
export const postKeyValueRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}
export const postRequest = (url,params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: qs.stringify(params)
        /*headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }*/
    })
}

export const putRequest = (url,params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        params: params
    })
}

export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        params: params
        /*headers:{
            'Content-Type': 'application/json;charset=UTF-8'
        }*/
    })
}
export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        params: params
    })
}