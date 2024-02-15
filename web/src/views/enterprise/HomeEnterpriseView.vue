<template>
    <div>
        <el-container class="el-container" >
            <el-aside class="el-aside"><AsideBar /></el-aside>
            <el-main class="el-main">
                <el-row class="el-row">
                    <el-col :span="3" class="el-col-di1">{{ pageStore.pageName }}</el-col>
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
                <ProblemView  v-if="pageStore.nowPage==='problems'" />
                <AddProblemView v-if="pageStore.nowPage==='addproblem1'" />
                <GetGradeView v-if="pageStore.nowPage==='getgrade'" />
                <AddExamView v-if="pageStore.nowPage==='boardtest'" />
            </el-main>
        </el-container>
    </div>
</template>

<script lang="ts" setup>
import AsideBar from '@/components/AsideBar.vue';
import ProblemView from '@/views/enterprise/ProblemView.vue';
import AddProblemView from '@/views/enterprise/AddProblemView.vue';
import GetGradeView from '@/views/enterprise/GetGradeView.vue';
import AddExamView from '@/views/enterprise/AddExamView.vue';
import { usePageStore } from '@/store/page';
import { useUserStore } from '@/store/user';
import { useRouter } from 'vue-router';

const router = useRouter();
const pageStore = usePageStore();
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
}
.el-aside{
    width: 280px;
    background-color: var(--aside-bar-color);
}
.el-main{
    padding-top: 0px;
    padding-left: 0px;
    padding-right: 0px;
}
.el-row{
    background-color: rgb(240,248,255);
    height: 100px;
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
</style>
