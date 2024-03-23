<template>
    <el-container class="el-container">
        <el-aside class="el-aside"><AsideBar /></el-aside>
        <el-main class="el-main" v-if="containerVisable">
            <el-row class="el-row">
                <el-col :span="3" class="el-col-di1">{{ route.meta.title }}</el-col>
                <el-col :span="3" :offset="15" class="el-col-di7">
                    <el-dropdown trigger="click">
                        <img class="el-avatar" size="30" src="@/assets/defaultHeadImg.png" />
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="editEnterpriseInfo">企业资料</el-dropdown-item>
                                <el-dropdown-item @click="userStore.logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-col>
            </el-row>
            <RouterView />
        </el-main>
        <el-main v-if="!containerVisable" style="display: flex; justify-content: center;">
            <el-card class="el-card2">
                <el-icon class="el-icon-back" @click="()=>{containerVisable = !containerVisable}"><Back /></el-icon>
                <span class="info-span first-info-span">企业全称：{{ enterprise_user?.name }}</span><br><br>
                <span class="info-span">联系邮箱：{{ enterprise_user?.email }}</span>
            </el-card>
        </el-main>
    </el-container>
</template>

<script lang="ts" setup>
import AsideBar from '@/components/AsideBar.vue';
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import $ from 'jquery'
import { ElMessage } from 'element-plus';
import { Back } from '@element-plus/icons-vue'

const containerVisable = ref(true);
const route = useRoute()
const userStore = useUserStore();
interface enterprise { accountId: number, email: string, enterpriseId: number, name: string, }
const enterprise_user = ref<enterprise>();


function getUserInfo()  {
    $.ajax({
        url: 'http://127.0.0.1:3000/get/enterprise/information/',
        type: 'get',
        headers: {
            Authorization: 'Bearer ' + useUserStore().token,
        },
        success: (result:string)=>{
            const resp = JSON.parse(result);
            enterprise_user.value = JSON.parse(resp.enterprise_user);
            console.log(enterprise_user.value)
        },
        error: ()=>{
            ElMessage.error("失败");
        }
    })
}

function editEnterpriseInfo()   {
    getUserInfo();
    containerVisable.value = false;
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
    background-color: #fbfbfb;
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
    background-color: #fbfbfb;
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
.el-card2 {
    margin-top: 20px;
    width: 700px; 
    height: 500px;
}
.info-span {
    margin-left: 150px;
}
.first-info-span {
    display: flex;
    padding-top: 60px;
}
.el-icon-back {
    font-size: 30px;
    border-radius: 50%;
}
.el-icon-back :hover{
    background-color: #F5DEB3;
    border-radius: 50%;
    cursor: pointer;
}
</style>