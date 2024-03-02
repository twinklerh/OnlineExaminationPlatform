<template>
    <el-container class="el-container">
        <el-aside class="el-aside"><AsideBar /></el-aside>
        <el-main class="el-main">
            <el-row class="el-row">
                <el-col :span="3" class="el-col-di1">{{ route.meta.title }}</el-col>
                <el-col :span="3" :offset="15" class="el-col-di7">
                    <el-dropdown trigger="click">
                        <img class="el-avatar" size="30" src="@/assets/defaultHeadImg.png" />
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item>企业资料</el-dropdown-item>
                                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-col>
            </el-row>
            <RouterView />
        </el-main>
    </el-container>
</template>

<script lang="ts" setup>
import AsideBar from '@/components/AsideBar.vue';
import { useUserStore } from '@/store/user';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute()
const router = useRouter();
const userStore = useUserStore();

function logout(){
    localStorage.setItem('jwt_token','');
    userStore.token = "";
    router.push({ name:'login' });
}

</script>

<style scoped>
.el-container{
    height: 800px;
    display: flex;
    flex-shrink: 0;
}
.el-aside{
    position: fixed;
    width: 260px;
    height: 1080px;
    background-color: var(--aside-bar-color);
}
.el-main{
    margin-left: 280px;
    padding-top: 5px;
    padding-left: 0px;
    padding-right: 15px;
}
.el-row{
    background-color: rgb(240,248,255);
    height: 100px;
    border-radius: 19px;
}
.el-col-di1{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    font-size: 22px;
    border-radius: 3px;
}
.el-col-di7{
    border-radius: 10px;
    display: flex;
    justify-content: right;
    align-items: center;
    height: 55px;
    background-color: rgb(251,251,251);
}
.el-avatar{
    margin-top: 5px;
    margin-bottom: 5px;
    margin-right: 15px;
    border-radius: 50%;
}
.el-avatar:hover{
    cursor: pointer;
}
</style>
