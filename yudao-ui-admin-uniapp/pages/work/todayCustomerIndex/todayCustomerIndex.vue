<template>
	<view>
		<viewTop ref="VT" :scrollTop="top"></viewTop>
		<uni-list :border="true" v-for="(item, index) in swipeList" :key="index">
			<uni-card :title="item.downstreamName + '（' + item.downstreamAlias + ')'" :extra="'所属日期：' + item.orderDate">
				<!-- <text class="uni-body">所属日期: {{ item.orderDate }}，登记： {{item.registrationCount}} ，入库：{{item.warehousingCount}}，未到货：{{item.notWarehousingCount}}</text> -->
				<view>
					<uni-table>
						<uni-tr>
							<uni-td>登记:</uni-td>
							<uni-td>{{ item.registrationCount }}</uni-td>
							<uni-td>入库:</uni-td>
							<uni-td style="font-weight: bold; color: red; font-size: 20px;">{{item.warehousingCount}}</uni-td>
							<uni-td>未到货:</uni-td>
							<uni-td>{{item.notWarehousingCount}}</uni-td>
						</uni-tr>
						<uni-tr>
							<uni-td>总量:</uni-td>
							<uni-td>{{item.totalOrderNumber}}</uni-td>
							<uni-td/><uni-td/><uni-td/><uni-td/>
						</uni-tr>
					</uni-table>
				</view>
				<view slot="actions" class="card-actions">
					<view class="card-actions-item" @click="handleToTodayCustomerOrderIndex(item.downstreamAlias)">
						<uni-icons type="settings-filled" size="18" color="#999"></uni-icons>
						<text class="card-actions-item-text">详情</text>
					</view>
					<view class="card-actions-item" @click="oneClickToBalance(item.downstreamAlias)">
						<uni-icons type="cart-filled" size="18" color="#999"></uni-icons>
						<text class="card-actions-item-text">结算</text>
					</view>
					<!-- <view class="card-actions-item" @click="actionsClick('评论')">
						<uni-icons type="chatbubble" size="18" color="#999"></uni-icons>
						<text class="card-actions-item-text">评论</text>
					</view> -->
				</view>
			</uni-card>
			<!-- 自定义右侧内容 -->
			<!-- <uni-list-chat @dbclick="handleToTodayCustomerOrderIndex(item.downstreamAlias)"
				:title="item.downstreamAlias + '（' + item.downstreamName + ')'" 
				:note="'登记：' + item.registrationCount + '，入库：' + item.warehousingCount + '，未到货：' + item.notWarehousingCount" 
				badge-positon="right" 
				:badge-text="item.totalOrderNumber"
				link>
				<view class="chat-custom-right">
					<text class="chat-custom-text">总订单量: {{ item.totalOrderNumber }}</text>
					<text class="chat-custom-text">所属日期: {{ item.orderDate }}</text>
					<button
						type="warn" 
						plain="true" 
						class="warn-button-body"
						size="mini"
						@click="oneClickToBalance()"
						>一键结算
					</button>
					<uni-icons type="star-filled" color="#999" size="18"></uni-icons>
				</view>
			</uni-list-chat> -->
		</uni-list>
		<view>
			<uni-popup ref="popup" type="dialog">
				<uni-popup-dialog :content="messageText" 
				cancelText="关闭" 
				confirmText="确认" 
				:duration="2000" 
				:before-close="true" 
				@close="dialogClose" 
				@confirm="dialogConfirm">
				</uni-popup-dialog>
			</uni-popup>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	import viewTop from "./view-top/view-top";
	import { getTodayCustomerList } from "@/api/orders/orders"
	
	export default {
		components: { viewTop },
		data() {
			return {
				top:0,
				swipeList: [],
				messageText: "",
				msgType: "success",
			}
		},
		onLoad() {
		  this.getTodayCustomerList()
		},
		onPullDownRefresh() {
		    // 处理下拉刷新的逻辑
			this.getTodayCustomerList()
		    // 例如：停止下拉刷新
		    uni.stopPullDownRefresh();
		},
		onPageScroll(e) {
			 //调用子组件方法
			this.$refs.VT.topData(e.scrollTop);
		},
		methods: {
			getTodayCustomerList() {
				getTodayCustomerList(this.searchValue).then(response => {
					this.swipeList = response.data;
				})
			},
			handleToTodayCustomerOrderIndex(downstreamAlias) {
					this.$tab.navigateTo('/pages/work/todayCustomerIndex/todayCustomerOrderIndex?downstreamAlias=' + downstreamAlias)
			},
			oneClickToBalance(downstreamAlias) {
				this.orderType = 4;
				this.messageText = `请您确认是否对客户“${downstreamAlias}”已入库的订单进行结算？`
				this.$refs.popup.open('center')
			},
			dialogClose() {
				this.$refs.popup.close()
			},
			dialogConfirm() {
			}
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

.uni-card__header {
    display: flex;
    border-bottom: 1px #EBEEF5 solid;
    flex-direction: row;
    align-items: center;
    padding: 10px;
    overflow: hidden;
    font-weight: bold;
}

.chat-custom-text {
	font-size: 12px;
	color: #999;
}

.card-actions {
	display: flex;
	flex-direction: row;
	justify-content: space-around;
	align-items: center;
	height: 45px;
	border-top: 1px #eee solid;
}
.card-actions-item {
	display: flex;
	flex-direction: row;
	align-items: center;
}
.card-actions-item-text {
	font-size: 12px;
	color: #666;
	margin-left: 5px;
}

</style>
