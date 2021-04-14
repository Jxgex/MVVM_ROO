package com.example.stores.EditModule
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stores.EditModule.ViewModel.editViewMode
import com.example.stores.R
import com.example.stores.StoreAplicacion
import com.example.stores.common.Entity.StoreEntity
import com.example.stores.databinding.FragmentEditarBinding
import com.example.stores.mainModule.MainActivity
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditarFragment : Fragment() {
    //MVVM
    private lateinit var mEditViewModel: editViewMode
    //
    private lateinit var mBinding: FragmentEditarBinding
    private var mActivity: MainActivity? = null
    private var mIsEditMode: Boolean = false
    private var mStoreEntity: StoreEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEditViewModel = ViewModelProvider(requireActivity()).get(editViewMode::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentEditarBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ///MVVM
        setupViewModel()
        ///
        Cambios_Validacion()
    }

    private fun setupViewModel() {
        mEditViewModel.getStoreSelect().observe(viewLifecycleOwner,{
            mStoreEntity = it
            if(it.id!=0L){
                mIsEditMode = true
                setUiStore(it)
            }
            else{
                mIsEditMode = false
            }
            ActionBarFun()
        })
        mEditViewModel.getresult().observe(viewLifecycleOwner,{ result ->
            Ocultar_Teclado()
            when(result){
                is Long ->{
                    mStoreEntity!!.id = result
                    mEditViewModel.setStoreSelect(mStoreEntity!!)
                    Toast.makeText(activity, "Insertado correctamente", Toast.LENGTH_LONG).show()
                    mActivity?.onBackPressed()
                }
                is StoreEntity ->{
                    mEditViewModel.setStoreSelect(mStoreEntity!!)
                    Toast.makeText(activity, "Actualizado Correctamente", Toast.LENGTH_LONG).show()
                    mActivity?.onBackPressed()

                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_guardado,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                Ocultar_Teclado()
                mActivity?.onBackPressed()
                true
            }

            R.id.action_guardar -> {
                if (mStoreEntity != null && Validar_Campos(
                        mBinding.LayoutImgUrl, mBinding.LayoutDireccionWeb,
                        mBinding.LayoutTelefono, mBinding.LayoutNombre
                    )
                ) {
                    with(mStoreEntity!!) {
                        Nombre = mBinding.TxtNombreTienda.text.toString().trim()
                        Phone = mBinding.TxtNumero.text.toString().trim()
                        website = mBinding.TxtUrl.text.toString().trim()
                        ImgURL = mBinding.TxtUrlImg.text.toString().trim()
                    }
                    if (mIsEditMode) mEditViewModel.UpdateStore(mStoreEntity!!)
                    else mEditViewModel.SaveStore(mStoreEntity!!)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        mEditViewModel.setshowFab(true)
        mEditViewModel.setresult(Any())
        super.onDestroy()
    }

    /**
     * Métodos Propios
     * */
    private fun Ocultar_Teclado(){
        val teclado = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(view !=null){
            teclado.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    private fun Cambios_Validacion() {
        with(mBinding){
            TxtNombreTienda.addTextChangedListener {
                Validar_Campos(LayoutNombre)
            }
            TxtNumero.addTextChangedListener {
                Validar_Campos(LayoutTelefono)
            }
            TxtUrl.addTextChangedListener{
                Validar_Campos(LayoutDireccionWeb)
            }
            TxtUrlImg.addTextChangedListener {
                Validar_Campos(LayoutImgUrl)
                LoadImg(it.toString().trim())
            }
        }
    }

    private fun Validar_Campos(vararg Campos:TextInputLayout):Boolean{
        var isValid =  true
        for(campos in Campos){
            if(campos.editText?.text.toString().trim().isEmpty()){
                campos.error = getString(R.string.Validacion)
                isValid = false
            }
            else{
                campos.error = null
            }
        }
        return isValid
    }

    private fun LoadImg(url:String){
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.ImgUrl)
    }

    private fun ActionBarFun() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(mIsEditMode){
            mActivity?.supportActionBar?.title = getString(R.string.LUpdateTienda)
        }
        else{
            mActivity?.supportActionBar?.title = getString(R.string.LcrearTienda)
        }
        setHasOptionsMenu(true)
    }

    private fun setUiStore(storeEntity: StoreEntity){
        with(mBinding){
            TxtNombreTienda.setText(storeEntity.Nombre)
            TxtNumero.setText(storeEntity.Phone)
            TxtUrl.setText(storeEntity.website)
            TxtUrlImg.setText(storeEntity.ImgURL)
        }
    }
}