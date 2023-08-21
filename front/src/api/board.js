import axios from "axios";

const BASE_URL = "http://localhost:9244";

export default {
    getArticle: function(seq) {
        return axios.get(BASE_URL + `/api/article/detail?seq=${seq}`);
    },
    getArticles: function(type, page, size, keyword) {
        return axios.get(BASE_URL + `/api/article/read/${type}`,{
            params: {
                page:page,
                size:size,
                keyword: keyword
            }
        })
    },
    deleteArticle: function(miSeq, aiSeq) {
        return axios.delete(BASE_URL + `/api/article/delete?miSeq=${miSeq}&aiSeq=${aiSeq}`);
    },
    recommendArticle: function(miSeq, aiSeq) {
        return axios.post(BASE_URL + `/api/article/recommend?miSeq=${miSeq}&aiSeq=${aiSeq}`);
    },
    scrapArticle: function(miSeq, aiSeq) {
        return axios.post(BASE_URL + `/api/article/scrap?miSeq=${miSeq}&aiSeq=${aiSeq}`);
    },
    deleteComment: function(miSeq, ciSeq) {
        return axios.delete(BASE_URL + `/api/comment/delete?miSeq=${miSeq}&ciSeq=${ciSeq}`);
    } 
}