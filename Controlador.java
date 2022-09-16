/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Museo;
import modelo.RepositorioMuseo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vista.Vista;

/**
 *
 * @author juan
 */
public class Controlador implements ActionListener{

    RepositorioMuseo repositorio;
    Vista vista;

    public Controlador(RepositorioMuseo repositorio, Vista vista) {
        this.repositorio = repositorio;
        this.vista = vista;
        agregarEventos();
        listar();
    }
 
    private void agregarEventos() {
        vista.getjButtonAgregar().addActionListener(this);
        vista.getjButtonBorrar().addActionListener(this);
        vista.getjButtonActualizar().addActionListener(this);
        vista.getjButtonBuscar().addActionListener(this);
        
   
    }

    
    public void buscar(){
                
        Long idMuseo = Long.parseLong(vista.getjTextId().getText());
        
        Museo museo = repositorio.findById(idMuseo).get();        
        vista.getjTextCiudad().setText(museo.getCiudad());
        vista.getjTextNombre().setText(museo.getNombre());
        
    }   
             
    public List<Museo> listar(){
        
         
        List<Museo> listaMuseos = (List<Museo>) repositorio.findAll();
        JTable tabla = vista.getjTable();
        
        int row = 0;
        for (Museo s : listaMuseos){
            tabla.setValueAt(s.getIdMuseo(), row, 0);
            tabla.setValueAt(s.getNombre(), row, 1);
            tabla.setValueAt(s.getCiudad(), row, 2);
            row++;
        }
        
        for (int i = row; i < tabla.getRowCount(); i++) {
            tabla.setValueAt("", row, 0);
            tabla.setValueAt("", row, 1);
            tabla.setValueAt("", row, 2);
        }
        
        return listaMuseos;       
    }   
    public void agregar(){
        
        Museo museo = Museo.crearMuseo(vista.getjTextCiudad().getText(), vista.getjTextNombre().getText());
        repositorio.save(museo);
        
    }   
                  
    public void eliminar(){
                    
        JTable tabla = vista.getjTable();
        Long idMuseo = (Long) tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
        if (idMuseo > -1){
            repositorio.deleteById(idMuseo);
        }
    }     
    
                      
    public void actualizar(){
    
        Long idBusqueda = Long.valueOf(vista.getjTextId().getText());
        String nombreBusqueda = vista.getjTextNombre().getText();
        String ciudadBusqueda =vista.getjTextCiudad().getText();
        
        Museo museo = repositorio.findById(idBusqueda).get();
        museo.setNombre(nombreBusqueda);
        museo.setCiudad(ciudadBusqueda);
        repositorio.save(museo);
        
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        
            if (e.getSource() == vista.getjButtonAgregar()){
                agregar();
                listar();
            }
            
            if (e.getSource() == vista.getjButtonBorrar()){
                eliminar();
                listar();
                            
            }
            
            if (e.getSource() == vista.getjButtonBuscar()){
                buscar();
                listar();
                            
            }            
    
            
            if (e.getSource() == vista.getjButtonActualizar()){
                actualizar();
                listar();
                            
            }    

    }
}
