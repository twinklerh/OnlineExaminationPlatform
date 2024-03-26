<template>
  <router-view/>
</template>

<script lang="ts" setup>
import { onBeforeMount } from 'vue';
import { useUserStore } from './store/user';

onBeforeMount(()=>{
  const token = localStorage.getItem("jwt_token");
  if(token!=null && token!='undefine')   useUserStore().token = token;
})

</script>

<style>
</style>
