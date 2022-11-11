package com.amy.common.design.demo2.lc;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author xuqingxin
 * 字符串类型题
 */
public class A003StringQuestion {


    /**
     * <p>
     * +-------------------------------------------+
     * |    排序加双指针
     * +-------------------------------------------+
     * </p>
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int length = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        //便利
        for (int i = 0; i < length; i++) {
            //特殊条件这个已经有序了,就不要主元后面的
            if (nums[i] > 0) {
                break;
            }
            //去重和前一个一样吗???i代表主元pivot其他左右指针都要去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            //左右移动指针
            // left在pivot后, right是右边界
            int left = i + 1, right = length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                //全部区间大于小于等于
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    //避免重复,策略不同left是忘右走看右边+1
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;                    //right往左走-1
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                }
            }
        }
        return ans;
    }


    /**
     * <p>
     * +-------------------------------------------+
     * |  无重复字符的最长子串
     * +-------------------------------------------+
     * </p>
     */
    public static int max, left;
    public static HashMap<Character, Integer> cache = Maps.newHashMap();

    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }

        for (int i = 0; i < s.length(); i++) {
            if (cache.containsKey(s.charAt(i))) {
                left = Math.max(left, cache.get(s.charAt(i)));
            }
            cache.put(s.charAt(i), i);
            max = Math.max(max, i - left);
        }
        return max;
    }

    /**
     * <p>
     * +-------------------------------------------+
     * | test
     * +-------------------------------------------+
     * </p>
     */
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferedReader.readLine();
        StringBuilder sb = new StringBuilder(s);
        String str1 = sb.substring(1, s.length() - 1);

        // String s1 = bufferedReader.readLine();
        // StringBuilder sb1 = new StringBuilder(s);
        // String str2 =  sb.substring(1,s.length()-1);
        System.out.println(str1);
        System.out.println(lengthOfLongestSubstring(str1));

    }


}
