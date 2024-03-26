<template>
	<view>
		<viewTop ref="VT" :scrollTop="top"></viewTop>
		<uni-list :border="true" v-for="(item, index) in swipeList" :key="index">
			<!-- 自定义右侧内容 -->
			<uni-list-chat @click="handleToTodaySupplierOrderIndex(item.upstreamAlias)"
				:title="item.upstreamAlias + '（' + item.upstreamName + ')'" 
				:note="'登记：' + item.registrationCount + '，入库：' + item.warehousingCount + '，未到货：' + item.notWarehousingCount" 
				badge-positon="right" 
				:badge-text="item.totalOrderNumber"
				link>
				<view class="chat-custom-right">
					<text class="chat-custom-text">总订单量: {{ item.totalOrderNumber }}</text>
					<text class="chat-custom-text">所属日期: {{ item.orderDate }}</text>
					<!-- 需要使用 uni-icons 请自行引入 -->
					<!-- <uni-icons type="star-filled" color="#999" size="18"></uni-icons> -->
				</view>
			</uni-list-chat>
		</uni-list>
	</view>
</template>

<script>
	import viewTop from "./view-top/view-top";
	import { getTodaySupplierList } from "@/api/orders/orders"
	
	export default {
		components: { viewTop },
		data() {
			return {
				top:0,
				swipeList: [],
			}
		},
		onLoad() {
		  this.getTodaySupplierList()
		},
		onPullDownRefresh() {
		    // 处理下拉刷新的逻辑
			this.getTodaySupplierList()
		    // 例如：停止下拉刷新
		    uni.stopPullDownRefresh();
		},
		onPageScroll(e) {
			 //调用子组件方法
			this.$refs.VT.topData(e.scrollTop);
		},
		methods: {
			getTodaySupplierList() {
				getTodaySupplierList(this.searchValue).then(response => {
					this.swipeList = response.data;
				})
			},
			handleToTodaySupplierOrderIndex(upstreamAlias) {
					this.$tab.navigateTo('/pages/work/todaySupplierIndex/todaySupplierOrderIndex?upstreamAlias=' + upstreamAlias)
			},
		}
	}
</script>

<style>

.chat-custom-right {
	flex: 1;
	/* #ifndef APP-NVUE */
	display: flex;
	/* #endif */
	flex-direction: column;
	justify-content: space-between;
	align-items: flex-end;
}

/deep/ .uni-list-chat__header-warp {
    position: relative;
    display: none;
}

.chat-custom-text {
	font-size: 12px;
	color: #999;
}


</style>
