package com.example.simple.simplethink.main.UserInfoActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.MyApp.Companion.context
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.updatepassword.UpdatePasswordActivity
import com.example.simple.simplethink.model.UploadFileResponse
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.vip.VIPCenterActivity
import kotlinx.android.synthetic.main.activity_update_userinfo.*
import me.iwf.photopicker.PhotoPicker
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import android.provider.MediaStore
import android.graphics.Bitmap
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import com.example.simple.simplethink.base.BaseActivity
import kotlinx.android.synthetic.main.activity_totle.*
import kotlinx.android.synthetic.main.activity_vip_center.view.*
import java.io.*
import java.util.regex.Pattern


/**
 * Created by mobileteam on 2019/8/13.
 */
class UserInfoActivity : BaseActivity(), UserInfoContract.View {
    override fun onRefreshUerInfoSuccess() {
        initUserInfoView()
        Toast.makeText(this, R.string.update_user_info_success, Toast.LENGTH_LONG).show()
    }

    private var presenter: UserInfoContract.Presenter = UserInfoPresenter()

    override fun onUpdateUserInfoSuccess() {
        presenter.loadUserInfo()
    }

    override fun onUploadFileSuccess(message: UploadFileResponse) {
        val url = message.url
        presenter.updateUserInfo(url, null)
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            var intent = Intent(context, UserInfoActivity::class.java)
            return intent
        }
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_userinfo)
        presenter.bind(this)
        initView()
    }

    private fun initView() {
        initUserInfoView()
        registerTitle.setOnClickListener {
            finish()
        }
        update_nick_name.setOnClickListener {
            val intent = UpdateNickNameActivity.newIntent(this)
            startActivity(intent)
        }
        enter_vip_center.setOnClickListener {
            val intent = VIPCenterActivity.newIntent(this)
            startActivity(intent)
        }
        update_password.setOnClickListener {
            val intent = UpdatePasswordActivity.newIntent(this)
            startActivity(intent)
        }
        user_info_avatar.setOnClickListener {
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(true)
                    .setShowGif(false)
                    .setPreviewEnabled(false)
                    .start(this, PhotoPicker.REQUEST_CODE)
        }
    }

    override fun onResume() {
        initUserInfoView()
        super.onResume()
    }

    private fun initUserInfoView() {
        val userInfo = AuthInstance.getInstance().userInfo
        Glide.with(context!!).load(userInfo?.avatar).apply(RequestOptions().placeholder(R.drawable.photo)).into(user_info_avatar)
        update_nick_name_text.text = userInfo?.nickName
        var phoneNo = ""
        val pattern = Pattern.compile("^[0-9]*$")
        userInfo?.userName?.let { if(pattern.matcher(userInfo?.userName.toString()).matches()) phoneNo =  userInfo?.userName }
        update_phone_number.text = phoneNo
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                val photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                val photoPath = photos[0]
                val inputUri = Uri.fromFile(File(photoPath))
                Glide.with(this).load(inputUri).apply(RequestOptions().placeholder(R.drawable.photo)).into(user_info_avatar)
                val avata = File(photoPath)
                var body = RequestBody.create(MediaType.parse("image/jpg"), avata)
                var filePart = MultipartBody.Part.createFormData("file", avata.getName(), body);
                presenter.uploadFile(filePart)
            }
        }
    }
}