package br.com.greedzxn.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.greedzxn.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController 
@RequestMapping("/tasks")
public class TaskController {

    @Autowired 
    private ITaskRepository taskRepository;

    @PostMapping("/createTask")
    public ResponseEntity createTask(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        taskModel.setIdUser((UUID) request.getAttribute("idUser"));

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("As datas de inicio e termino devem ser depois da data atual.");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("A data de inicio deve ser antes da data de termino.");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
       }

    @PostMapping("/listTasks")
    public List<TaskModel> listTasks(HttpServletRequest request) {
        return this.taskRepository.findByIdUser((UUID)request.getAttribute("idUser"));
    }

    @PutMapping("/{id}")
    //trocar de task model para ResponseEntity
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);

        // validar se tarefa existe
        
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Essa tarefa não existe");
        }
        

        // validar se o usuario eh o mesmo dono da tarefa

        var idUser = request.getAttribute("idUser");

        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Usuário não tem permissão para acessar essa tarefa");
        }

        Utils.copyNonNullProperties(taskModel, task);

        return ResponseEntity.status(HttpStatus.OK).
            body(this.taskRepository.save(task));
    }
}
