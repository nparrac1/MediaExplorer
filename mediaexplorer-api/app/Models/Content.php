<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Content extends Model
{
    protected $keyType = 'string';
    public $incrementing = false;

    protected $fillable = [
        'id',
        'title',
        'tipo',
        'description',
        'imagen',
        'categoria_id'
    ];

    public function category(): BelongsTo
    {
        return $this->belongsTo(Category::class, 'categoria_id');
    }
}