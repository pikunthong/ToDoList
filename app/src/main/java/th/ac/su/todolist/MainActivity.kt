package th.ac.su.todolist

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var itemList:ArrayList<String> = ArrayList<String>()
    lateinit var arrayAdapter:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList.add("Whtch TV")
        itemList.add("Play ROV")
        itemList.add("Netflix")

        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,itemList)

//        var ListView1 = findViewById<ListView>(R.id.ListView)
        ListView.adapter = arrayAdapter
        registerForContextMenu(ListView)
//        registerForContextMenu(btnAdd)
        btnAdd.setOnClickListener {



            if(edtInput.length() == 0 || edtInput.equals("") || edtInput == null)
            {
                Toast.makeText(this,"is Empty", Toast.LENGTH_SHORT).show()
            }



            itemList.add(0,edtInput.text.toString())

            arrayAdapter.notifyDataSetChanged()//เพื่อบอกว่ามีดารรเปลี่ยนแปลง data เพื่อการ update



            hideKeyboard()
            edtInput.getText().clear()


        }

        ListView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, itemList[position], Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main_context,menu)

        super.onCreateContextMenu(menu, v, menuInfo)
    } //    เมนู remove edit

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        when(item.itemId){

            R.id.action_remove -> {
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()//เพื่อบอกว่ามีดารรเปลี่ยนแปลง data เพื่อการ update มันถึงจะแสดงหน้าจอ
            }
            R.id.action_edit -> {
                Toast.makeText(this,itemList[position], Toast.LENGTH_SHORT).show()
            }

        }


        return super.onContextItemSelected(item)
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
