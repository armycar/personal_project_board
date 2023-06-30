<template>
    <div>
       <el-row>
        <el-col :span="2"></el-col>
        <el-col :span="20">
            <el-container>
                <el-header><h3>{{ article.category ? "[" + article.category + "]" : "" }} {{ article.title }}</h3></el-header>
                <el-main><h1>{{article.nickname}}</h1> <div class="dot"></div> {{ formatRegdt(article.regdt) }}</el-main>
                <hr>
                <el-main>{{article.detail}}</el-main>
                  <template v-if="article.img && article.img.length > 0">
              <div v-for="image in article.img" :key="image.apFileUrl" class="img-wrapper" style="text-align: left;">
                <img :src="`http://localhost:9244/api/download/img/article/${image.apFileUrl}`" />
              </div>
            </template>
                
                <a class="recommend_btn" @click="recommendArticle">
                <img src="/images/thumbsup.png">
                <br>
                <br>
                <br>
                <span class="lcount">{{ article.lcount }}</span>
                </a>
                <br>
                <br>
                <br>  
                <el-main>
                    <h2>댓글</h2>
                    <br>
                    <template v-if="article.comment && article.comment.length > 0">
            <div class="comment-table-wrapper">
            <table>
                <tbody>
                <tr v-for="comment in article.comment" :key="comment.ciId">
                    <td>{{ comment.ciDetail }}</td>
                    <td>{{ formatRegdt(comment.ciRegDt) }}</td>
                </tr>
                </tbody>
            </table>
            </div>
            </template>
        <div class="card my-4" style="width: 500px; margin-left: 30px;">
              <div class="card-body">
                  <el-form name="comment-form" @submit.prevent="writeComment">
                      <div class="form-group">
                        <textarea class="form-control" rows="5" placeholder="댓글을 입력해주세요" v-model="formData.detail" style="width: 500px;" >
                        </textarea>
                      </div>
                      <button type="submit" class="btn btn-primary">등록</button>
                  </el-form>
              </div>
          </div>
        </el-main>
            </el-container>
            </el-col>
            <el-col :span="2"></el-col>
       </el-row>

        <br />
        <el-button @click="goBack">뒤로가기</el-button>
        <el-button v-if="showDeleteButton" @click="deleteArticle">삭제</el-button>
        <el-button v-if="showUpdateButton" @click="updateArticle">수정</el-button>
        
    </div>
</template>

<script>
import apiBoard from "@/api/board";
import axios from 'axios';

export default {
    name: 'DetailPage',
    data() {
    return {
    article: "", 
    formData: {
      detail: ""
    },
}
},

mounted() {
  this.seqValue = sessionStorage.getItem('token');

apiBoard.getArticle(this.$route.params.seq)
	.then((response) => {
        console.log("getArticle",response);
		this.article = response.data;
    this.aiSeq = this.article.aiSeq;
	})
        .catch((e) => {
        console.log(e);
	});
    },

    computed: {
      showDeleteButton() {
        return this.article && this.article.miSeq === +sessionStorage.getItem('token');
      },
      showUpdateButton() {
        return this.article && this.article.miSeq === +sessionStorage.getItem('token');
      }
    },
    methods: {
     
    writeComment() {
      if(!this.formData.detail) {
        this.$message.error("내용을 입력해주세요");
        return;
      }

      if(!this.seqValue) {
        this.$message.error("로그인이 필요합니다");
        return;
      }
      const formData = new FormData();
      formData.append("detail", this.formData.detail);

      axios.put(`http://localhost:9244/api/comment/write?miSeq=${this.seqValue}&aiSeq=${this.aiSeq}`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
        this.$message({
          message: '댓글이 등록 되었습니다', //새로고침 추가하기
          type: 'success'
        })
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
        this.$message.error("댓글 작성중 에러발생");
      })
    } , 

    formatRegdt(regdt) {
    
    const date = new Date(regdt);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  },
        goBack() {
            this.$router.go(-1);
        },
        deleteArticle() {
          if(!this.article) {
            console.log("article is not defined");
            return;
          }
            this.$confirm('정말로 삭제하시겠습니까? ', '경고', {
              confirmButtonText: '확인',
              cancelButtonText: '취소',
              type: 'warning'
            }) 
            .then(() => {
              apiBoard.deleteArticle(sessionStorage.getItem('token'), this.article.aiSeq)
              // this.$router.push('/');
              this.$message({
              message: '게시글이 삭제되었습니다',
              type: 'success'
            });
              this.$router.push('/');
            })
            .catch((e) => {
              console.log(e);
            });
        },
        recommendArticle() {
          if(!this.seqValue) {
            this.$message.error('로그인이 필요합니다');
            return;
          }

            apiBoard.recommendArticle(sessionStorage.getItem('token'), this.article.aiSeq)
            .then((response) => {
              console.log(response);
              window.location.reload();
            })
            .catch((error) => {
              console.log(error);
              this.$message.error("게시글 추천중 에러발생")
            })
          }
        },
        formatComment(row) {
     if (row.comment && Array.isArray(row.comment)) {
        return row.comment.map(c => `${c.ciDetail}\n${this.formatRegdt(c.ciRegDt)}`).join("\n\n");
      } else {
        return "";
      }
        }
    }


</script>

<style>
.comment-table-wrapper {
  margin-left: 30px;
}
td {
  padding-right: 100px; /* 원하는 간격으로 조정 */
  padding-bottom: 50px;
}
.el-header h3 {
    /* background-color: #e9eef3; */
    color: #333;
    text-align: left;
    line-height: 60px;
    font-size: 33px;
}
.el-main {
    /* background-color: #e9eef3; */
    color: #333;
    text-align: left;
    margin-top: 20px;
    font-size: 20px;
}
.el-main h1{
    /* background-color: #e9eef3; */
    color: #333;
    text-align: left;
    margin-top: 20px;
    font-size: 22px;
}
.el-main h2{
    /* background-color: #e9eef3; */
    color: #333;
    text-align: left;
    margin-top: 20px;
    font-size: 25px;
}
.img-wrapper {
  margin-bottom: 20px;
}
.img-wrapper img {
  max-width: 100%;
}
.recommend_btn {
   position: fixed;
  bottom: 50%;
  right: 50%;
  transform: translate(50%, 50%);
  width: 80px;
  height: 80px;
  border: 0.05px  black;
  border-radius: 50px;
  background: #f6f4f4e5;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.05);
  z-index: 10;
  
  display: inline-block;
  cursor: pointer;
}
.recommend_btn img {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -55%);
}
hr {
    border: none;
    border-top: 1px solid #bbb;
    margin: 20px 0;
    margin-bottom: 5px;
}
dot { 
  display: inline-block;
  width: 5px;
  height: 5px;
  margin-left: 5px;
  margin-right: 5px;
  border-radius: 50%;
  background-color: black;
}
</style>
