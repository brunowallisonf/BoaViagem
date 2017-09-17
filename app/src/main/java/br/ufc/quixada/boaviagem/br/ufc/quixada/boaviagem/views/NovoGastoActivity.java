package br.ufc.quixada.boaviagem.br.ufc.quixada.boaviagem.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import br.ufc.quixada.boaviagem.R;
import br.ufc.quixada.boaviagem.models.Gasto;
import br.ufc.quixada.boaviagem.models.GastoRepository;
import br.ufc.quixada.boaviagem.models.ViagemRepository;

public class NovoGastoActivity extends Activity {
    private Spinner local;
    private Spinner categoria;
    private ViagemRepository viagemRepository;
    private Date dateGasto;
    private Button botaoData;
    private EditText descricao;
    private EditText valor;
    private GastoRepository gastoRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_gasto);
        viagemRepository= new ViagemRepository();
        local = findViewById(R.id.localSpinner);
        categoria =  findViewById(R.id.categoriaSpinner);
        botaoData = findViewById(R.id.buttonData);
        descricao = findViewById(R.id.descricao);
        valor = findViewById(R.id.valor);
        ArrayAdapter<CharSequence> adapte = ArrayAdapter.createFromResource(this,R.array.categoriasGasto,android.R.layout.simple_spinner_item);
        categoria.setAdapter(adapte);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,viagemRepository.getLocais());
        local.setAdapter(adapter);
        gastoRepository = new GastoRepository();
    }
    public void clickData(View view){
        showDialog(R.id.buttonData);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        int ano = 0;
        int mes = 0;
        int dia = 0;
        DatePickerDialog.OnDateSetListener dataListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                botaoData.setText(i+"/"+i1+"/"+i2);
                dateGasto = criarData(i,i1,i2);
            }
        };
        if (id==R.id.buttonData){
           return new DatePickerDialog(this,dataListener,ano,mes,dia);
        }
        return null;
    }

    private Date criarData(int anoSelecionado, int mesSelecionado, int diaSelecionado)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(anoSelecionado, mesSelecionado, diaSelecionado);
        return calendar.getTime();
    }
    public void criarGasto(View view){
        try {
            Gasto g = new Gasto();
            g.setData(dateGasto);
            g.setCategoria(categoria.getSelectedItem().toString());
            g.setDescricao(descricao.getText().toString());
            g.setLocal(local.getSelectedItem().toString());
            g.setValor(Float.parseFloat(valor.getText().toString()));
            gastoRepository.addGasto(g);
            Toast t = Toast.makeText(this, "Gasto criado com sucesso", Toast.LENGTH_LONG);
            t.show();
        }catch (Exception e){
            Toast t = Toast.makeText(this, "erro ao criar gasto", Toast.LENGTH_LONG);
            t.show();
        }
    }

}