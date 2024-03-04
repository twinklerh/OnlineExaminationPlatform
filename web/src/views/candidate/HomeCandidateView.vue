<template>
    <div>
        <el-button type="primary" @click="userStore.logout()">退出</el-button>
    </div>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';

const userStore = useUserStore()
</script>

<style>
</style>