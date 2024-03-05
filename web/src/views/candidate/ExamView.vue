<template>
    <div style="display: flex; justify-content: left;  flex-flow: wrap; padding: 0px 20px;">    <!--一行排不下，flex-flow实现自动换行-->
        <el-card class="el-col-item" v-for="(item,index) in list" :key="index">{{ item }}
        </el-card>
    </div>
    <el-button type="success" style="position: absolute; margin-top:3px; margin-left: 45px;">邀请码</el-button>
    <el-button type="primary" style="margin-left: 720px; margin-top:9px"></el-button>
</template>

<script lang="ts" setup>
const list = [1,2,3];
</script>

<style scoped>
.el-col-item{
    background-color: red;
    margin: 5px 15px;
    height: 190px;
    width: 290px;
    border-radius: 12px;
}
</style>