<template>
<b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/">Board</b-navbar-brand>
<b-navbar-nav>
        <a v-if="isLoggedIn" class="username">{{ username }}님 안녕하세요</a>
      </b-navbar-nav>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
    </b-collapse>
  </b-navbar>
  <br><br>
            <div>
              <el-row>
                <el-col :span="2"></el-col>
                <el-col :span="20">
                    <el-container>
                        <el-header><h3>{{ article.category ? "[" + article.category + "]" : "" }} {{ article.title }}</h3></el-header>
                      <el-main>
          <div style="display: flex; align-items: center; ">
            <div v-if="article.url === null">
              <div style="width: 70px; height: 70px; margin-right: 10px; margin-top: 20px; border-radius: 50%; background-color: white; border: 0.1px solid black;"></div>
            </div>
            <div v-else>
              <img :src="`http://localhost:9244/api/download/img/member/${article.url}`" style="width: 70px; height: 70px; margin-right: 10px; margin-top: 20px; object-fit: cover; border-radius: 50%; border: 0.1px solid black;">
            </div>
                  <div>
                    <h1>{{article.nickname}}</h1>
                    <div class="dot"></div>
                    <span>{{ formatRegdt(article.regdt) }}</span>
                  </div>
                </div>
              </el-main>
                <hr>
                <el-main>{{article.detail}}</el-main>
                  <template v-if="article.img && article.img.length > 0">
              <div v-for="image in article.img" :key="image.apFileUrl" class="img-wrapper" style="width: 550px; ">
                <img :src="`http://localhost:9244/api/download/img/article/${image.apFileUrl}`" style="width: 100%; height: 100%; object-fit: cover;"/>
              </div>
            </template>
              <div class="button-container">
                 <div class="recommend_btn" @click="recommendArticle">
                    <span class="recommend_btn_text">추천</span>
                <br>
                <br>
                <span class="lcount">{{ article.lcount }}</span>
                </div>
                 <div class="scrap_btn" @click="scrapArticle">
                    <span class="scrap_btn_text">스크랩</span>
                    <img src="/images/bookmark.png">
                 </div>
              </div>
                  <br>
                  <br>
                <el-main>
                    <h2>댓글</h2>
                    <br>
                    <template v-if="article.comment && article.comment.length > 0">
            <div class="comment-table-wrapper">
            <table>
                <tbody>
                <tr v-for="comment in article.comment" :key="comment.ciSeq">
                  <!--<td>
                    <div v-if="comment.mpFileUrl === null">
                      <div style="width: 50px; height: 50px; border-radius: 50%; background-color: white; border: 0.1px solid black;"></div>
                    </div>
                    <div v-else>
                      <img :src="`http://localhost:9244/api/download/img/member/${comment.mpFileUrl}`" style="width: 50px; height: 50px; border-radius: 50%; border: 0.1px solid black;">
                    </div>
                  </td>-->
                    <!-- <td>{{ comment.ciMiSeq }}</td> -->
                    <td>{{ comment.miNickName}}</td> 
                    <td>{{ comment.ciDetail }}</td>
                    <td>{{ formatRegdt(comment.ciRegDt) }}</td>
                    <td>
                    <el-button @click="editComment(comment)">수정</el-button>
                    <el-button @click="deleteComment(comment)">삭제</el-button>
                    </td>
                </tr>
                </tbody> 
            </table>
            </div>
            </template>
        <div v-if="showModal" class="modal-wrapper">
      <div class="modal-content">
        <h3>댓글 수정</h3>
        <div class="form-group">
          <textarea class="form-control" rows="5" placeholder="댓글을 입력해주세요" v-model="editFormData.detail"></textarea>
        </div>
        <div class="button-group">
          <button class="btn btn-primary" @click="updateComment(comment)">수정 완료</button>
          <button class="btn btn-secondary" @click="closeModal">닫기</button>
        </div>
      </div>
    </div>
    <div class="comment-form-wrapper">
            <el-form name="comment-form" @submit.prevent="writeComment" class="comment-form">
          <div class="form-group">
            <textarea class="form-control" rows="5" placeholder="댓글을 입력해주세요" v-model="formData.detail"></textarea>
          </div>
          <button type="submit" class="btn btn-primary">등록</button>
        </el-form>
    </div>
        </el-main>
            </el-container>
            </el-col>
            <el-col :span="2"></el-col>
       </el-row>

        <br />
        <el-button @click="goBack">뒤로가기</el-button>
        <el-button v-if="showDeleteButton" @click="deleteArticle">삭제</el-button>
        <el-button v-if="showUpdateButton" class="update-button">
          <router-link :to="{ path: '/api/article/update', query: { aiSeq: article.aiSeq } }">수정</router-link>
          </el-button>
        
    </div>
</template>

<script>
import apiBoard from "@/api/board";
import axios from 'axios';

export default {
    name: 'DetailPage',
    data() {
    return {
      showModal: false,
    article: "", 
    formData: {
      detail: ""
    },
    editFormData: {
      detail: ""
    },
    aiSeq: null
}
},

mounted() {
  this.seqValue = sessionStorage.getItem('token');
  this.aiSeq = null;

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
      isLoggedIn() {
      return !!sessionStorage.getItem('token');
    },
    username() {
      return sessionStorage.getItem('username');
    },
      showDeleteButton() {
        return this.article && this.article.miSeq === +sessionStorage.getItem('token');
      },
      showUpdateButton() {
        return this.article && this.article.miSeq === +sessionStorage.getItem('token');
      },
      showCommentDeleteButton() {
  if (this.seqValue && this.article && this.article.comment) {
    return this.article.comment.some(comment => comment.ciMiSeq === +this.seqValue);
  }
  return false;
},
    },
    methods: {
      closeModal() {
        this.showModal = false;
      },
      editComment(comment) {
        if (parseInt(this.seqValue) === comment.ciMiSeq) {
          this.editFormData = {
            detail: comment.ciDetail,
          };
        this.showModal = true;
        this.selectedComment = comment;
        }else {
          this.$message.error("댓글작성자만 수정이 가능합니다")
        }
      },
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
    deleteComment(comment) {
      if (parseInt(this.seqValue) === comment.ciMiSeq) {
        this.$confirm('정말로 삭제하시겠습니까?','경고', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        type: 'warning'
      })
      .then(() => {
        apiBoard.deleteComment(sessionStorage.getItem('token'), comment.ciSeq)
        this.$message({
          message: '댓글이 삭제되었습니다',
          type: 'success'
        });
        window.location.reload();
      })
      .catch((e) => {
        console.log(e);
      });
  } else {
    this.$message.error("댓글 작성자만 삭제가 가능합니다")
  }
    },
    updateComment() {
      const comment = this.selectedComment;
      if(parseInt(this.seqValue) === comment.ciMiSeq) {
        const formData = new FormData();
      formData.append("detail", this.editFormData.detail);
         axios
        .patch(`http://localhost:9244/api/comment/update?miSeq=${this.seqValue}&ciSeq=${comment.ciSeq}`, 
        formData, 
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
        )
        .then((response) => {
          console.log(response);
          this.$message({
          message: '댓글이 수정 되었습니다',
          type: 'success'
        })
      window.location.reload();
        })
        .catch((error) => {
          console.log(error);
          this.$message.error("게시물 수정중 에러발생");
        });
      }
      else {
        this.$message.error("댓글 작성자만 수정이 가능합니다")
      }
     
    },

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
          },
        scrapArticle() {
          if(!this.seqValue) {
            this.$message.error('로그인이 필요합니다');
            return;
          }
            apiBoard.scrapArticle(sessionStorage.getItem('token'), this.article.aiSeq)
            .then((response) => {
              console.log(response);
              this.$message({
              message: '게시물을 스크랩 하였습니다',
              type: 'success'
        })
            })
            .catch((error) => {
              console.log(error);
              this.$message.error("게시물 스크랩중 에러발생")
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
.update-button a{
  text-decoration: none;
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
  margin-left: auto;
  margin-right: auto;
  text-align: center;
}
.img-wrapper img {
  max-width: 100%;
}

.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
}

.recommend_btn, .scrap_btn {
  width: 80px;
  height: 80px;
  border-radius: 50px;
  background: #f6f4f4e5;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.05);
  z-index: 10;
  cursor: pointer;
  margin: 10px; /* 버튼 간격 조정 */
}

.recommend_btn_text {
  position: relative;
  top: 32%
}

.scrap_btn_text {
  position: relative;
  top: 32%;
  left: 10%;
}

.scrap_btn img {
  position: relative;
  top: 58%;
  left: -30%;
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
.comment-form-wrapper {
  width: 500px;
  margin-left: 2%;
}

.lcount {
  text-decoration: none;
}

.username {
  margin-right: 16px;
  padding: 8px 16px;
  font-size: 14px;
  text-decoration: none;
  cursor: pointer;
}

</style>
