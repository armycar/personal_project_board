<template>
  <el-row>
    <el-col :span="2"></el-col>
    <el-col :span="20">
      <el-card>
        <div>
          <router-link :to="{ path: '/' }">
            <el-button type="text" class="left-button">Back</el-button>
          </router-link>

          <span>게시물 수정하기</span>

          <el-button @click="updateArticle" type="text" class="right-button">수정</el-button>
        </div>
        <br /><br />

        <div>
          <el-form
            :label-position="right"
            label-width="100px"
            :model="formData"
          >
            <el-form-item label="제목">
              <el-input
                type="text"
                v-model="formData.title"
                placeholder="제목을 입력하세요"
              ></el-input>
            </el-form-item>
            <el-form-item label="본문">
              <el-input
                type="textarea"
                v-model="formData.detail"
                placeholder="본문을 입력하세요"
                rows="10"
              ></el-input>
            </el-form-item>
            <el-form-item label="이미지추가">
                <input type="file" @change="handleFileUpload">
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </el-col>
    <el-col :span="2"></el-col>
  </el-row>
</template>

<script>
import axios from 'axios'; // axios import 추가
import apiBoard from "@/api/board";

export default {
  created() {
    const aiSeq = this.$route.query.aiSeq;
    if (aiSeq) {
  // aiSeq를 이용한 초기 데이터 로딩 등 처리
  apiBoard.getArticle(aiSeq)
    .then((response) => {
      console.log("getArticle", response);
      this.article = response.data;
    })
    .catch((e) => {
      console.log(e);
    });
} else {
  // aiSeq가 없을 때 처리
  console.log("aiSeq is not defined");
}
  },
  props: {
    seq: {
      type: Number,
      required: true,
      default: 0
    },
    cateSeq: {
      type: Number,
      required: true,
      default: 1
    }
  },

  data() {
    return {
      formData: {
        title: "",
        detail: ""
      },
      seqValue: null,
      aiSeq: null
    };
  },

  mounted() {
    this.seqValue = sessionStorage.getItem('token');
    if (!this.seqValue) {
      this.$message.warning('로그인이 필요합니다');
      this.$router.push('/');
    }

  },

  methods: {
    handleFileUpload(event) {
        const file = event.target.files[0];
        this.formData.img = file;
    },
     
    updateArticle() {

const aiSeq = this.$route.query.aiSeq;
      if (!aiSeq) {
        console.log("aiSeq is not defined");
        return;
      }
      const formData = new FormData();
      formData.append("title", this.formData.title);
      formData.append("detail", this.formData.detail);
      
      if(this.formData.img) {
      formData.append("img", this.formData.img);
      }

      axios
        .patch(`http://localhost:9244/api/article/update?miSeq=${this.seqValue}&aiSeq=${aiSeq}`, formData, { // seq와 cateSeq에 props의 값 대입
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((response) => {
          console.log(response);
          this.$router.push({ path: '/' });
          this.$message({
          message: '게시글이 수정 되었습니다',
          type: 'success'
        })
        })
        .catch((error) => {
          console.log(error);
          this.$message.error("게시물 수정중 에러발생");
        });
    },
  },
};
</script>

<style scoped>
.left-button {
    float: left;
    padding: 3px 0;
}
.right-button {
    float: right;
    padding: 3px 0;
}
</style>