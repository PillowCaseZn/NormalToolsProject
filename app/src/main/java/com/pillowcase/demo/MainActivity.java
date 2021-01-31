package com.pillowcase.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String[] data = new String[]{
            "1",
            "2",
            "3",
    };

    private String result;

    @SuppressLint({"SetTextI18n", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            log("onCreate", "");
            final TextView infoTv = findViewById(R.id.info_tv);
            final AppCompatImageView imageView = findViewById(R.id.image);
        } catch (Exception e) {
            error(e, "onCreate");
        }
    }

    @Override
    public void onBackPressed() {
        log("onBackPressed", "");
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        log("onPause", "");
        super.onPause();
    }

    @Override
    protected void onResume() {
        log("onResume", "");
        super.onResume();
    }

    public int maxProfit(int[] prices) {
        int data = 0;
        if (prices.length > 0) {
            int date = 0;
            while (date < prices.length) {
                if (date == prices.length - 1) {
                    break;
                }
                for (int i = date + 1; i < prices.length; i++) {
                    int num = prices[i] - prices[date];
                    if (num > data) {
                        data = num;
                    }
                }
                date++;
            }
        }
        return data;
    }

    public int maxProfit2(int[] prices) {
        int data = 0;
        if (prices.length > 0) {
            for (int i = 0; i < prices.length; i++) {
                if (i < prices.length - 1) {
                    if (prices[i] < prices[i + 1]) {
                        data += prices[i + 1] - prices[i];
                        log("maxProfit2", "num1 : " + prices[i] + " , num2 : " + prices[i + 1] + " , data : " + data);
                    }
                }
            }
        }
        return data;
    }

    public int[] plusOne(int[] digits) {
        if (digits.length > 0) {
            List<Integer> dataList = new ArrayList<>();
            for (int i : digits) {
                dataList.add(i);
            }
            int lastIndex = dataList.get(dataList.size() - 1) + 1;
            dataList.set(dataList.size() - 1, lastIndex);
            while (dataList.contains(10)) {
                for (int i = dataList.size() - 1; i >= 0; i--) {
                    if (dataList.get(i) == 10) {
                        dataList.set(i, 0);
                        if (i == 0) {
                            Collections.sort(dataList);
                            dataList.add(1);
                            Collections.reverse(dataList);
                        } else {
                            dataList.set(i - 1, dataList.get(i - 1) + 1);
                        }
                    }
                }
            }
            digits = new int[dataList.size()];
            for (int i = 0; i < dataList.size(); i++) {
                digits[i] = dataList.get(i);
            }
        }
        return digits;
    }

    public int removeDuplicates(int[] nums) {
        log("removeDuplicates", "nums : " + Arrays.toString(nums));
        int len = 0;
        if (nums != null) {
            for (int i = 0; i < nums.length; i++) {
                if (i == 0) {
                    len++;
                } else {
                    int lastNum = nums[i - 1];
//                    log("removeDuplicates", "i : " + i + " , currentNum : " + nums[i] + " , LastNum : " + lastNum);
                    if (nums[i] < lastNum) {
                        nums[i] = nums[i + 1];
//                        log("removeDuplicates", "CurrentNum < LastNum , nums : " + Arrays.toString(nums));
                    }

                    for (int j = i; j < nums.length; j++) {
                        int currentNum = nums[j];
//                        log("removeDuplicates", "j : " + j + " , currentNum : " + currentNum + " , LastNum : " + lastNum);
                        if (currentNum > lastNum) {
                            nums[i] = nums[j];
                            len++;
                            break;
                        } else if (j == nums.length - 1) {
                            nums[i] = nums[j];
//                            log("removeDuplicates", "j == nums.length, nums : " + Arrays.toString(nums));
                        }
                    }
                }
            }
        }
        log("removeDuplicates", "--> len : " + len + " , nums : " + Arrays.toString(nums));
        return len;
    }

    public boolean containsDuplicate(int[] nums) {
        boolean isFound = false;
        if (nums != null && nums.length > 1) {
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                if (i + 1 <= nums.length - 1 && nums[i] == nums[i + 1]) {
                    isFound = true;
                    break;
                }
            }
        }

        return isFound;
    }

    public int singleNumber(int[] nums) {
        if (nums != null) {
            Arrays.sort(nums);
            log("singleNumber", "nums : " + Arrays.toString(nums));
            if (nums.length == 1) {
                return nums[0];
            }
            int last, current, next;
            for (int i = 0; i < nums.length; i++) {
                current = nums[i];
                log("singleNumber", "i : " + i + " , current : " + current);
                if (i == 0) {
                    next = nums[i + 1];
                    if (current == next) {
                        i++;
                    } else {
                        log("singleNumber", "Current != Next  -->current : " + current);
                        return current;
                    }
                } else {
                    last = nums[i - 1];
                    if (current == last) {
                        i++;
                    } else {
                        if (i + 1 < nums.length) {
                            next = nums[i + 1];
                            if (current == next) {
                                i++;
                            } else {
                                log("singleNumber", "Current != Next  -->current : " + current);
                                return current;
                            }
                        } else {
                            log("singleNumber", "Last Nums -->current : " + current);
                            return current;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public int[] twoSum(int[] nums, int target) {
        int[] targetNums = null;

        for (int i = 0; i < nums.length; i++) {
            int num1 = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int num2 = nums[j];
                if (num1 + num2 == target) {
                    targetNums = new int[]{i, j};
                }
            }
        }
        log("twoSum", "targetNums : " + Arrays.toString(targetNums));
        return targetNums;
    }

    public void rotate(int[] nums, int k) {
        log("rotate", "nums : " + Arrays.toString(nums));
        if (nums != null && k != 0) {
            for (int i = 0; i < k; i++) {
                int last = nums[nums.length - 1];
                for (int j = nums.length - 1; j > 0; j--) {
                    nums[j] = nums[j - 1];
                }
                nums[0] = last;
            }
        }
        log("rotate", "--> nums : " + Arrays.toString(nums));
    }

    public void moveZeroes(int[] nums) {
        log("moveZeroes", "nums : " + Arrays.toString(nums));
        if (nums != null && nums.length != 1) {
            int index = 0, count = nums.length - 1;
            while (index < count) {
                int current = nums[index];
                if (current == 0) {
                    for (int j = index; j < nums.length - 1; j++) {
                        log("moveZeroes", "change--> j : " + j + " , item : " + nums[j]);
                        nums[j] = nums[j + 1];
                    }
                    nums[nums.length - 1] = current;
                    log("moveZeroes", "index : " + index + " , change-->item : " + Arrays.toString(nums));
                    count--;
                } else {
                    index++;
                }
            }
        }
        log("moveZeroes", "--> nums : " + Arrays.toString(nums));
    }

    public void log(String method, Object object) {
    }

    public void warn(String method, String message) {
    }

    public void error(Throwable throwable, String method) {
    }
}
