<template>
  <RouterView />
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { onBeforeMount } from 'vue';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();

onBeforeMount(async()=>{
  userStore.token = localStorage.getItem('jwt_token') as string;
  if(userStore.token) await userStore.getUserInfo();
  if(userStore.token && userStore.status==='enterprise') router.push({name: 'enterprise'});
  else  router.push({ name: 'login' })
})


</script>

<style scoped>
</style>
