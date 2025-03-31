import request from '@/utils/request'

//完成对axios的进一步封装

/**
 * 通用的post请求
 * @param {*} url  请求的地址
 * @param {*} data  请求的数据
 * @returns 
 */
export async function Post(url, data) {
    let response = {
        Code: "",
        Data: "",
        Msg: "",
        Success: true,
    };
    await request({
        url: url,
        method: "post",
        data: data || {},
    })
        .then((res) => {
            response = res;
        })
        .catch((ex) => {
            response.Success = false;
            response.Msg = ex;
        });
    return response;
}