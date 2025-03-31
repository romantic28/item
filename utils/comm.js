import { Message, MessageBox, Loading } from "element-ui"; //消息提示框
import store from "@/store";
import router from '@/router'

/**
 * 公共确认提示框
 * @param {*} config
 * @returns
 */
export function ConfirmMessageBox(config) {
    let _config = {
        title: config.title || "提示",
        content: config.content || "",
    };
    return new Promise(function (resolve, reject) {
        MessageBox.confirm(_config.content, _config.title, {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
        })
            .then(() => {
                resolve(true);
            })
            .catch(() => {
                reject(false);
            });
    });
}


export default {
    ConfirmMessageBox
};