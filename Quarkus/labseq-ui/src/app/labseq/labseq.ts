import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-labseq',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './labseq.html',
  styleUrls: ['./labseq.css']
})
export class LabseqComponent {

  n = 0;
  result: any = null;
  loading = false;

  private apiUrl = 'http://localhost:8080/labseq';

  constructor(private http: HttpClient) {}

  calculate() {
    this.loading = true;
    this.http.get<any>(`${this.apiUrl}/${this.n}`).subscribe({
      next: r => {
        this.result = r.value;
        this.loading = false;
      },
      error: (err) => {
		  console.error(err); 
        alert('Error calling backend');
        this.loading = false;
      }
    });
  }

  reset() {
    this.loading = true;
    this.http.post(`${this.apiUrl}/reset`, null).subscribe({
      next: () => {
        alert('Cache reset');
        this.loading = false;
      },
      error: () => {
        alert('Error resetting cache');
        this.loading = false;
      }
    });
  }
}
