<template>
    <div class="parent-div">
        <el-card class="el-card">
            <span class="exam-title-span">{{ testpaperTitle }}</span>
            <el-divider></el-divider>
        </el-card>
    </div>

</template>

<script lang="ts" setup>
import { useRoute } from 'vue-router';
const route = useRoute();
const testpaperTitle = decodeURIComponent(route.query.title as string);
</script>

<style scoped>
.el-card{
    margin: 5px auto;
    width: 1080px;
    background-color: #F0FFFF;
    border-radius: 17px;
}
.exam-title-span{
    font-size: 24px;
    display: flex;
    justify-content: center;
    font-weight: bold;
    letter-spacing: 5px;
}
</style>