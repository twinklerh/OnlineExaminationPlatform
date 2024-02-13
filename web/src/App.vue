<template>
  <div></div>
  <RouterView />
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();

if(!userStore.token)  {
  router.push({name: 'login'});
}

</script>

<style scoped>
</style>
