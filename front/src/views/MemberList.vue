<template>
  <div>
  <b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="/">Board</b-navbar-brand>

<b-navbar-nav>
        <a v-if="isLoggedIn" class="username">{{ username }}님 안녕하세요</a>
      </b-navbar-nav>
  </b-navbar>
</div>  


</template>

<script>

import apiMember from '@/api/member';

export default {
    data() {
        return {
            members: null,
            page: 1,
            totalPages: 0,
        };
    },
    methods: {
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
    }
}
</script>
<style scoped>

</style>
