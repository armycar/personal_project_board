<!-- 회원관리 눌렀을때 보여지는 페이지 -->
<template>
  <div>
  <b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/">Board</b-navbar-brand>

<b-navbar-nav>
        <a v-if="isLoggedIn" class="username">{{ username }}님 안녕하세요</a>
      </b-navbar-nav>
    <b-collapse id="nav-collapse" is-nav>
    </b-collapse>

    <b-navbar-nav style="margin-left: auto;">
      <b-nav-form @submit.stop.prevent="search">
        <b-form-input v-model="searchKeyword" size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
        <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
      </b-nav-form>

      <b-nav-item-dropdown text="Type" right>
      <b-dropdown-item @click="setSearchType('nickname')">닉네임</b-dropdown-item>
      <b-dropdown-item @click="setSearchType('status')">활동상태</b-dropdown-item>
      </b-nav-item-dropdown>
    </b-navbar-nav>

  </b-navbar>
</div>  

<el-table :data="members" style="width: 100%">
  <el-table-column prop="miSeq" label="회원번호" align="center"></el-table-column>
  <el-table-column prop="miNickName" label="닉네임" align="center"></el-table-column>
  <el-table-column prop="miEmail" label="이메일" align="center"></el-table-column>
  <el-table-column prop="miStatus" label="활동상태" align="center"></el-table-column>
  <el-table-column prop="miRegDt" label="가입일" align="center"></el-table-column>
</el-table>

  <div class="pagination-container">
    <el-pagination
    @current-change="handlePageChange"
    :current-page="page"
    :page-size="5"
    :total="totalPages * 5"
    layout="prev, pager, next">
    </el-pagination>
  </div>
</template>

<script>

import apiMember from '@/api/member';
import moment from 'moment';

export default {
    data() {
        return {
            members: null,
            page: 1,
            totalPages: 0,
        };
    },
    mounted() {
      this.loadMemberList();
    },
    computed: {
      getPagerCount() {
        return Math.ceil(this.totalPages / 5);
      },
      isLoggedIn() {
      return !!sessionStorage.getItem('token');
    },
    username() {
      return sessionStorage.getItem('username');
    }
    }
    ,
    methods: {
      setSearchType(type) {
        this.searchType = type;
      },
      search() {
        if(this.searchKeyword.trim() !== '') {
          const size = 5;

          apiMember.getMemberList(this.searchType, this.page-1, size, this.searchKeyword)
          .then((response) => {
            this.members = response.data.content.map((member) => {
              member.miRegDt = moment(member.miRegDt).format('YYYY-MM-DD HH:mm:ss');
              return member;
            });
            const totalCount = response.data.totalElements;
              this.totalPages = Math.ceil(totalCount / size);
          })
          .catch((e) => {
            console.log(e);
          })
        }
      },
      loadMemberList() {
      const type = 'all';
      const size = 5;

      apiMember
        .getMemberList(type, this.page-1, size)
        .then((response) => {
          console.log('getMembers', response);
          this.members = response.data.content.map((member) => {
            member.miRegDt = moment(member.miRegDt).format('YYYY-MM-DD HH:mm:ss');
            return member;
          });
          const totalCount = response.data.totalElements;
          this.totalPages = Math.ceil(totalCount / size);

          console.log(totalCount)
          console.log(this.page)
        })
        .catch((e) => {
          console.log(e);
        });
    },
    handlePageChange(newPage) {
      this.page = newPage;
      this.loadMemberList();
    }
    }
}
</script>
<style scoped>

.pagination-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

</style>
