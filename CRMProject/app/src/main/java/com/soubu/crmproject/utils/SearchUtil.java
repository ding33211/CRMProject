package com.soubu.crmproject.utils;

import android.content.Context;

import com.soubu.crmproject.R;

/**
 * 查找类工具
 * Created by dingsigang on 16-8-31.
 */
public class SearchUtil {

    /**
     * 在数组中找index
     * @param arrays
     * @param object
     * @return  默认返回0
     */
    public static int searchInArray(Object[] arrays, Object object){
        int index = 0;
        if(arrays == null){
            return 0;
        }
        for(Object o : arrays){
            if(o.equals(object)){
                return index;
            } else {
                index++;
            }
        }
        return 0;
    }

    public static CharSequence[] searchClueStateArray(Context context){
        return context.getResources().getStringArray(R.array.clue_status);
    }

    public static CharSequence[] searchClueStateWebArray(Context context){
        return context.getResources().getStringArray(R.array.clue_status_web);
    }

    public static CharSequence[] searchClueSourceArray(Context context){
        return context.getResources().getStringArray(R.array.clue_source);
    }

    public static CharSequence[] searchClueSourceWebArray(Context context){
        return context.getResources().getStringArray(R.array.clue_source_web);
    }

    public static CharSequence[] searchCustomerSizeArray(Context context){
        return context.getResources().getStringArray(R.array.customer_size);
    }

    public static CharSequence[] searchCustomerSizeWebArray(Context context){
        return context.getResources().getStringArray(R.array.customer_size_web);
    }

    public static CharSequence[] searchCustomerTypeArray(Context context){
        return context.getResources().getStringArray(R.array.customer_type);
    }

    public static CharSequence[] searchCustomerTypeWebArray(Context context){
        return context.getResources().getStringArray(R.array.customer_type_web);
    }

    public static CharSequence[] searchCustomerPropertyArray(Context context){
        return context.getResources().getStringArray(R.array.customer_property);
    }

    public static CharSequence[] searchCustomerPropertyWebArray(Context context){
        return context.getResources().getStringArray(R.array.customer_property_web);
    }

    public static CharSequence[] searchCustomerStateArray(Context context){
        return context.getResources().getStringArray(R.array.customer_status);
    }

    public static CharSequence[] searchCustomerStateWebArray(Context context){
        return context.getResources().getStringArray(R.array.customer_status_web);
    }

    public static CharSequence[] searchBusinessOpportunityStateArray(Context context){
        return context.getResources().getStringArray(R.array.business_opportunity_status);
    }

    public static CharSequence[] searchBusinessOpportunityStateWebArray(Context context){
        return context.getResources().getStringArray(R.array.business_opportunity_status_web);
    }

    public static CharSequence[] searchBusinessOpportunityTypeArray(Context context){
        return context.getResources().getStringArray(R.array.business_opportunity_type);
    }

    public static CharSequence[] searchBusinessOpportunityTypeWebArray(Context context){
        return context.getResources().getStringArray(R.array.business_opportunity_type_web);
    }

    public static CharSequence[] searchContractStateArray(Context context){
        return context.getResources().getStringArray(R.array.contract_state);
    }

    public static CharSequence[] searchContractStateWebArray(Context context){
        return context.getResources().getStringArray(R.array.contract_state_web);
    }

    public static CharSequence[] searchContractTypeArray(Context context){
        return context.getResources().getStringArray(R.array.contract_type);
    }

    public static CharSequence[] searchContractTypeWebArray(Context context){
        return context.getResources().getStringArray(R.array.contract_type_web);
    }

    public static CharSequence[] searchContractPayMethodArray(Context context){
        return context.getResources().getStringArray(R.array.contract_pay_method);
    }

    public static CharSequence[] searchContractPayMethodWebArray(Context context){
        return context.getResources().getStringArray(R.array.contract_pay_method_web);
    }

    public static CharSequence[] searchContractReviewStateArray(Context context){
        return context.getResources().getStringArray(R.array.contract_review_state);
    }

    public static CharSequence[] searchContractReviewStateWebArray(Context context){
        return context.getResources().getStringArray(R.array.contract_review_state_web);
    }


}
