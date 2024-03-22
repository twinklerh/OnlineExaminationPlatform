<template>
    <div class="container">
        <TableCardComp style="margin-top: 30px;" :data_checkedPaper="item" v-for="(item,index) in data_checkedPaper" :key="index">
            <template #button>
                <el-button type="primary" @click="getDetail(item.testpaper_title)">查看</el-button>
                <el-button v-if="item.is_score_public==false" type="primary" @click="announceScore(item)" style="margin-right: 10px;">公布成绩</el-button>
                <span v-else style="margin:auto 20px;">已公布</span>
            </template>
        </TableCardComp>    
    </div>
    <el-pagination @current-change="changePage" :page-size="8" :total="dataCount" layout="prev, pager, next" background />
</template>

<script lang="ts" setup>
import TableCardComp from '@/components/TableCardComp.vue';
import $ from 'jquery';
import { useUserStore } from '@/store/user';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
const router = useRouter();
interface CheckedTestPaper {begin_time:string, end_time:string, is_score_public:boolean, testpaper_title:string}
const data_checkedPaper = ref<CheckedTestPaper[]>([]);
const dataCount = ref(8);

changePage(1);

function changePage(pageNum:number) {   //  获取candidate_exam和exam的信息
    $.ajax({
        url: 'http://127.0.0.1:3000/enterprise/get/unchecked/tesepaper/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        data: {
            current_page: pageNum,
        },
        success: (result:string)=>{
            const resp = JSON.parse(result);
            dataCount.value = parseInt(resp.total);
            if(data_checkedPaper.value != null)  data_checkedPaper.value = JSON.parse(resp.jsonArray);
        },
        error: ()=>{
            ElMessage.error("失败");
        }
    })
}

const getDetail = (param_testPaperTitle:string) => {   //  创建新页面
    const url = router.resolve({name: 'checkdetail', query: { title: encodeURIComponent(param_testPaperTitle) }}).href;
    window.open(url);
}

function announceScore(item:CheckedTestPaper) {
    const testpaper_title = item.testpaper_title;
    item.is_score_public = true;
    $.ajax({
        url: 'http://127.0.0.1:3000/enterprise/announce/score/',
        type: 'post',
        headers: {
            Authorization: 'Bearer ' + useUserStore().token,
        },
        data: {
            testPaperTitle: testpaper_title
        },
        success: (result:string)=>{
            const resp = JSON.parse(result);
            ElMessage({type: 'success', message: resp.error_message});
        },
        error: ()=>{
            console.log("error");
        }
    })
}
</script>


<style scoped>
.container {
    display: flex;
    justify-content: left;
    height: 550px;
    width: 98%;
    border: 1px solid;
    border-color: rgb(151, 151, 151, 0.3);  /*越小越透明*/
    border-radius: 5px;
    margin: 17px auto;
}
</style>