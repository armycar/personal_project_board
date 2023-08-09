import axios from "axios";

const BASE_URL = "http://localhost:9244";

export default {
    loginMember: function(email, pwd) {
        const data = {
            email: email,
            pwd: pwd
        };
        return axios.post(BASE_URL+`/api/member/login`, data);
    },
    joinMember: function(email, pwd, pwdconfirm, nickname) {
        const data = {
            email: email,
            pwd: pwd,
            pwdconfirm: pwdconfirm,
            nickname: nickname
        };
        return axios.put(BASE_URL+`/api/member/join`, data);
    },
    dupchkEmail: function(email) {
        const data = {
            email: email
        };
        return axios.post(BASE_URL+`/api/member/dupchkEmail`, data);
    },
    dupchkName: function(nickname) {
        const data = {
            nickname: nickname
        };
        return axios.post(BASE_URL+`/api/member/dupchkNickName`, data);
    },
    getMember: function(seq) {
        return axios.get(BASE_URL+`/api/member/get/memberInfo/${seq}`);
    },
    leaveMember: function(seq) {
        return axios.post(BASE_URL+`/api/member/leave/${seq}`);
    },
    updateMemberInfo: function(seq, nickname, oldpwd, pwd, pwdconfirm) {
        const data = {
            nickname: nickname,
            oldpwd: oldpwd,
            pwd: pwd,
            pwdconfirm: pwdconfirm
        }
        return axios.patch(BASE_URL+`/api/member/change/memberInfo/${seq}`, data);
    },
    searchMemberPwd: function(email) {
        const data = {
            email: email
        };
        return axios.post(BASE_URL+`/api/member/search/pwd`, data);
    },
    getMemberArticle: function(seq) {
        return axios.get(BASE_URL+`/api/member/get/memberInfo/article/${seq}`);
    },
    getMemberComment: function(seq) {
        return axios.get(BASE_URL+`/api/member/get/memberInfo/comment/${seq}`);
    },
    getMemberScrap: function(seq) {
        return axios.get(BASE_URL+`/api/member/get/memberInfo/scrap/${seq}`);
    }
}