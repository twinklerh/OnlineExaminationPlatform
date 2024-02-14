<template>
    <div>
        <el-container class="el-container" >
            <el-aside class="el-aside"><AsideBar /></el-aside>
            <el-main class="el-main">
                <el-row>
                    <el-col :span="3"></el-col>
                    <el-col :span="3"></el-col>
                    <el-col :span="3"></el-col>
                    <el-col :span="3"></el-col>
                    <el-col :span="3"></el-col>
                    <el-col :span="3"></el-col>
                    <el-col :span="3" class="el-col-di5">
                        <el-avatar class="el-avatar" shape="circle" size="30" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
                    </el-col>
                    <el-col :span="3"></el-col>
                    <el-col :span="3"></el-col>
                    <el-col :span="3"></el-col>
                </el-row>
                <ProblemView  v-if="pageStore.nowPage==='problems'" />
                <AddProblemView v-if="pageStore.nowPage==='addproblem'" />
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

const pageStore = usePageStore();

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
    padding-top: 6px
}
.el-col-di5{
    border-radius: 10px;
    display: flex;
    justify-content: right;
    align-items: center;
    background-color: rgb(243, 243, 243);
}
.el-avatar{
    margin-top: 5px;
    margin-bottom: 5px;
    margin-right: 15px;
}
</style>
