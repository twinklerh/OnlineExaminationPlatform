<template>
    <div class="container">
        <TableCardComp style="margin-top: 30px;" :data_checkedPaper="item" v-for="(item,index) in data_checkedPaper" :key="index">
            <template #button>
                <el-button type="primary" @click="getDetail(item.testpaper_title)">查看</el-button>
            </template> 
        </TableCardComp>
    </div>
    <!-- <el-pagination /> -->
</template>

<script lang="ts" setup>
import TableCardComp from '@/components/TableCardComp.vue';
import { useTestpaperStore } from '@/store/testpaper';
import $ from 'jquery';
import { useUserStore } from '@/store/user';
import { TestpaperInterface } from '@/store/testpaper';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();
interface CheckedTestPaper {begin_time:string, end_time:string, ischecked:boolean, testpaper_title:string}
const testpaperStore = useTestpaperStore();
const data_checkedPaper = ref<CheckedTestPaper[]>([]);
testpaperStore.getTestPaper(1, async()=>{ await getCheckMsg(); });

const getDetail = (param:string) => {   //  创建新页面
    console.log(param)
    const url = router.resolve({name: 'checkdetail', query: { title: encodeURIComponent(param) }}).href;
    window.open(url);
}

async function getCheckMsg():Promise<void> {
    const arr:string[] = [];
    useTestpaperStore().testpaperlist.forEach((item:TestpaperInterface)=>{arr.push(item.title);})
    await $.ajax({
        url: 'http://127.0.0.1:3000/get/check/message/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + useUserStore().token,
        },
        data: {
            testpaperTitle: JSON.stringify(arr),
        },
        success: (result:string)=>{
            const resp = JSON.parse(result);
            if(data_checkedPaper.value != null)  data_checkedPaper.value = resp;
        },
        error: ()=>{
            console.log(77)
        }
    })
}
</script>


<style scoped>
.container {
    display: flex;
    justify-content: center;
    height: 550px;
    width: 98%;
    border: 1px solid;
    border-color: rgb(151, 151, 151, 0.3);  /*越小越透明*/
    border-radius: 5px;
    margin: 17px auto;
}
</style>