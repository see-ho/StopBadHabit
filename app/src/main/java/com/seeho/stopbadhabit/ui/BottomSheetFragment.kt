package com.seeho.stopbadhabit.ui


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.seeho.stopbadhabit.R
import com.seeho.stopbadhabit.data.model.Habit.Habit
import com.seeho.stopbadhabit.databinding.FragmentBottomSheetBinding
import com.seeho.stopbadhabit.ui.viewmodel.MainViewModel
import com.seeho.stopbadhabit.util.fragment.LifeType
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentBottomSheetBinding.inflate(layoutInflater) }
    private val mainViewModel : MainViewModel by activityViewModels()
    private var settingLife : LifeType = LifeType.Error
    private var mMode : Int = -1
    private var mName : String = ""


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = (super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }
        })as BottomSheetDialog
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val items = requireActivity().resources.getStringArray(R.array.goaldate_string_array)

        val mAdapter = ArrayAdapter(binding.root.context, R.layout.custom_spinner_date,R.id.tv_spinner_date, items)

        binding.spinner.adapter = mAdapter

        var mGoalDate = 15

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        mGoalDate=15
                        if(mMode != -1) setLife(mMode,mGoalDate)
                    }
                    1 -> {
                        mGoalDate=30
                        if(mMode != -1) setLife(mMode,mGoalDate)
                    }
                    2 ->{
                        mGoalDate=50
                        if(mMode != -1) setLife(mMode,mGoalDate)
                    }
                    3->{
                        mGoalDate=0
                        if(mMode != -1) setLife(mMode,mGoalDate)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                mGoalDate=15
            }
        }

        binding.btnEasymode.setOnClickListener {
            mMode = 0
            setLife(mMode,mGoalDate)
            binding.btnEasymode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_easymode_choose_2)

            binding.btnHardmode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hardmode)
            binding.btnNormalmode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_normalmode)
        }

        binding.btnNormalmode.setOnClickListener {
            mMode = 1
            setLife(mMode,mGoalDate)
            binding.btnNormalmode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_normalmode_choose)

            binding.btnEasymode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_easymode_1)
            binding.btnHardmode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hardmode)
        }

        binding.btnHardmode.setOnClickListener {
            mMode = 2
            setLife(mMode,mGoalDate)
            binding.btnHardmode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_hardmode_choose)

            binding.btnEasymode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_easymode_1)
            binding.btnNormalmode.background = ContextCompat.getDrawable(requireContext(),R.drawable.ic_normalmode)
        }

        binding.btnAdd.setOnClickListener {
            mName = binding.etName.text.toString()
            if(mMode == -1 && mName == ""){
                Snackbar.make(binding.root,String.format(requireActivity().resources.getString(R.string.bt_sheet_setNameMode)),Snackbar.LENGTH_SHORT).show()}
            else if(mMode == -1){
                Snackbar.make(binding.root,String.format(requireActivity().resources.getString(R.string.bt_sheet_setMode)),Snackbar.LENGTH_SHORT).show()}
            else if(mName == ""){
                Snackbar.make(binding.root,String.format(requireActivity().resources.getString(R.string.bt_sheet_setName)),Snackbar.LENGTH_SHORT).show()
            }
            else {
                val mDate = LocalDate.now()
                mainViewModel.insertHabit(Habit(
                    name=mName,
                    goal_date = mGoalDate,
                    start_date = mDate.toString(),
                    setting_life = settingLife.life,
                    current_life = settingLife.life,
                    state = 0,
                    mode = mMode
                ))
                dismiss()
            }
        }
        return binding.root
    }

    private fun setLife(mMode:Int, mGoalDate:Int) : LifeType {
        settingLife = when {
            mMode==0 && mGoalDate==15 -> LifeType.Easy15
            mMode==0 && mGoalDate==30 -> LifeType.Easy30
            mMode==0 && mGoalDate==50 -> LifeType.Easy50
            mMode==1 && mGoalDate==15 -> LifeType.Normal15
            mMode==1 && mGoalDate==30 -> LifeType.Normal30
            mMode==1 && mGoalDate==50 -> LifeType.Normal50
            mMode==2 && mGoalDate==15 -> LifeType.Hard15
            mMode==2 && mGoalDate==30 -> LifeType.Hard30
            mMode==2 && mGoalDate==50 -> LifeType.Hard50

            mMode==0 && mGoalDate==0 -> LifeType.Easy0
            mMode==1 && mGoalDate==0 -> LifeType.Normal0
            mMode==2 && mGoalDate==0 -> LifeType.Hard0

            else -> LifeType.Error
        }

        binding.tvLifeMessage.text = String.format(requireActivity().resources.getString(R.string.bt_sheet_life),settingLife.life)
        binding.tvLifeMessage.visibility = View.VISIBLE

        return settingLife
    }
}