<template>
    <el-container>
        <el-aside width="250px">
            <div style="display:flex; justify-content: left; align-items: center; margin-bottom: 12px;" >
                <img src="@/assets/logo.svg" style="height:30px;width:50px;"/>   
                <span>在线应试平台-后台管理</span>                
            </div>

            <el-divider style="margin: 0px auto;" />

            <el-menu style="height: 450px;" :default-active="route.name" router>
                <el-menu-item index="register">
                    <img src="@/assets/registerIcon.svg" class="icon">
                    <span>企业注册</span>
                </el-menu-item>
                <el-menu-item index="feedback">
                    <img src="@/assets/feedbackIcon.svg" class="icon">
                    <span>反馈处理</span>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <el-main>
            <el-dropdown trigger="click" class="icon-head">
                <span class="trigger-text">root</span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="exit">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </template>      
            </el-dropdown>
            <RouterView />
        </el-main>
    </el-container>
</template>

<script lang="ts" setup>
import { useRoute, useRouter } from 'vue-router';
const route = useRoute();
const router = useRouter();

function exit() {
    localStorage.setItem("jwt_token", "");
    router.push({name: 'login'});
}
</script>

<style scoped>
.icon {
    width: 30px;
    height: 30px;
    margin-right: 15px;
}
.trigger-text {
    background-color: skyblue;
    border-radius: 10%;
    height: 25px;
    width: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.icon-head {
    position: absolute;
    right: 15%;
}
.icon-head:hover {
    cursor: pointer;
}
</style>
